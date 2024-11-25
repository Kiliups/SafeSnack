<script lang="ts">
    import { currentUser, currentUserRoles } from '../../stores/user.svelte';
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';
    import type { UserMeta } from '../../types/UserMeta';
    import type { Authority} from "../../types/Authority";

    // logout function
    const logout = (async () => {
        try {
            let response = await fetch('http://localhost:8080/logout', {
                method: 'POST',
                credentials: 'include', // Ensure cookies (like JSESSIONID) are included in the request
            });

            if (!response.ok) {
                throw new Error('Failed to logout');
            }

            currentUser.set(null);  // Clear the user store
            currentUserRoles.set([]);  // Clear the user roles store

            await goto('/login'); // Redirect to the login page after logout
        } catch (error) {
            console.error('Error logging out:', error);
        }
    });

    // Fetch the current user when the component mounts
    onMount(async () => {
        try {
            let response = await fetch('http://localhost:8080/current-user', {
                method: 'GET',
                credentials: 'include', // Ensure cookies (like JSESSIONID) are included in the request
            });

            if (!response.ok) {
                throw new Error('Failed to fetch user data');
            }

            const data: UserMeta = await response.json();  // Type the response as UserMeta
            currentUser.set(data);  // Set the store with the fetched user data

            response = await fetch('http://localhost:8080/current-user-roles', {
                method: 'GET',
                credentials: 'include', // Ensure cookies (like JSESSIONID) are included in the request
            });

            if (!response.ok) {
                throw new Error('Failed to fetch user roles');
            }

            const roleData: Array<Authority> = await response.json();  // Type the response as UserMeta
            currentUserRoles.set(roleData);  // Set the store with the fetched user data
        } catch (error) {
            console.error('Error fetching current user:', error);
        }
    });
</script>

<style>
    /* Overall page styling */
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f7fb;
        color: #333;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        text-align: center;
    }

    /* Main container */
    .container {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        padding: 20px;
        width: 100%;
        max-width: 400px;
    }

    /* Heading styling */
    h1 {
        font-size: 2.5rem;
        color: #007bff;
        margin-bottom: 20px;
        text-transform: uppercase;
    }

    /* Button styling */
    button {
        width: 100%;
        padding: 12px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 50px;
        cursor: pointer;
        font-size: 1.1rem;
        transition: background-color 0.3s ease;
    }

    button:hover {
        background-color: #0056b3;
    }

    /* Paragraph styling */
    p {
        font-size: 1.2rem;
        color: #555;
        margin-bottom: 15px;
    }

    /* List of roles */
    ul {
        list-style-type: none;
        padding: 0;
        text-align: left;
        margin-top: 20px;
    }

    ul li {
        font-size: 1rem;
        color: #007bff;
        margin: 8px 0;
    }

    /* Responsive design */
    @media (max-width: 600px) {
        .container {
            padding: 15px;
        }

        h1 {
            font-size: 2rem;
        }

        button {
            font-size: 1rem;
        }
    }
</style>

<div class="container">
    {#if $currentUser}
        <h1>Hello, {$currentUser.name}!</h1>
        <p>Welcome back!</p>

        <a href="/admin">Dashboard</a>

        {#if $currentUserRoles.length > 0}
            <p>Your Roles:</p>
            <ul>
                {#each $currentUserRoles as role}
                    <li>{role["authority"]}</li>
                {/each}
            </ul>
        {/if}

        <!-- Logout button -->
        <button on:click={logout}>Logout</button>
    {:else}
        <h1>Hello, Guest!</h1>
        <p>Please log in to continue.</p>
        <a href="/login">Login</a>
    {/if}
</div>
