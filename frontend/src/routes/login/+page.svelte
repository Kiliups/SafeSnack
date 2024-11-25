<script lang="ts">
    import {goto} from '$app/navigation';
    import type {UserMeta} from "../../types/UserMeta";
    import type {Authority} from "../../types/Authority";
    import {currentUser, currentUserRoles} from "../../stores/user";

    let username = '';
    let password = '';
    let errorMessage = '';

    // Handle login form submission
    async function handleSubmit(event: SubmitEvent) {
        event.preventDefault();
        errorMessage = '';

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({username, password}),
                credentials: 'include',
            });

            if (response.ok) {
                await getUserDetails();
                await goto('/user/hello');
            } else {
                errorMessage = response.status === 401 ? (await response.text()) || 'Username or password is wrong. Please try again.' : 'An unexpected error occurred.';
                password = ''; // Clear the password field
            }
        } catch (err) {
            errorMessage = 'An error occurred. Please try again.';
            password = ''; // Clear the password field
            console.error(err);
        }
    }

    async function getUserDetails() {
        const [userResponse, rolesResponse] = await Promise.all([
            fetch('http://localhost:8080/current-user', {
                method: 'GET',
                credentials: 'include',
            }),
            fetch('http://localhost:8080/current-user-roles', {
                method: 'GET',
                credentials: 'include',
            })
        ]);

        if (userResponse.ok && rolesResponse.ok) {
            const userData: UserMeta = await userResponse.json();
            const roleData: Array<Authority> = await rolesResponse.json();

            currentUser.set(userData);
            currentUserRoles.set(roleData);
        } else {
            console.error('Failed to fetch user data or roles');
        }
    }
</script>

<style>
    .error-message {
        color: red;
        font-size: 0.875rem;
        margin-top: 0.5rem;
    }
</style>


<div class="min-h-screen bg-gray-100 flex items-center justify-center py-12">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-sm w-full">
        <h2 class="text-3xl font-semibold text-center text-gray-800 mb-6">Login</h2>

        <!-- Login form -->
        <form on:submit|preventDefault={handleSubmit} class="space-y-6">
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700">Email or Username</label>
                <input
                        id="email"
                        type="text"
                        bind:value={username}
                        class="mt-2 p-3 w-full border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
                        placeholder="Enter your email or username"
                        required
                />
            </div>

            <div>
                <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
                <input
                        id="password"
                        type="password"
                        bind:value={password}
                        class="mt-2 p-3 w-full border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
                        placeholder="Enter your password"
                        required
                />
            </div>

            <!-- Error message display -->
            {#if errorMessage}
                <div class="error-message">{errorMessage}</div>
            {/if}

            <div class="flex justify-center">
                <button
                        type="submit"
                        class="w-full py-3 px-6 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
                >
                    Login
                </button>
            </div>
        </form>

        <div class="text-center text-sm mt-4">
            <a href="/forgot-password" class="text-indigo-600 hover:text-indigo-800">Forgot your password?</a>
        </div>
    </div>
</div>