<script lang="ts">
    import {currentUser, currentUserRoles, isAdmin} from '../stores/user';
    import {goto} from '$app/navigation';

    let isMenuOpen = false; // State for mobile menu

    function navigateTo(route: string) {
        goto(route);
    }

    function toggleMenu() {
        isMenuOpen = !isMenuOpen; // Toggle the menu visibility
    }

    // logout function
    async function handleLogout() {
        try {
            const response = await fetch('http://localhost:8080/logout', {
                method: 'POST',
                credentials: 'include',
            });

            if (response.ok) {
                currentUser.set(null);
                currentUserRoles.set([]);
                await goto('/login');
            } else {
                console.error('Failed to logout');
            }
        } catch (error) {
            console.error('Error logging out:', error);
        }
    }
</script>

<nav class="bg-gray-800 text-white fixed top-0 left-0 w-full z-50 shadow-lg">
    <div class="container mx-auto px-4 flex justify-between items-center h-16">
        <!-- Logo -->
        <a href="/" class="text-xl font-bold text-white hover:text-indigo-400">SafeSnack</a>

        <!-- Hamburger Menu Button -->
        <button
                class="md:hidden block text-white focus:outline-none"
                on:click={toggleMenu}
                aria-label="Toggle Menu"
        >
            <svg
                    class="w-6 h-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
            >
                <path d="M3 6h18M3 12h18M3 18h18" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
            </svg>
        </button>

        <!-- Desktop Links -->
        <div class="hidden md:flex space-x-4">
            <a href="/" class="hover:text-indigo-400">Home</a>
            <a href="/user/profile" class="hover:text-indigo-400">Profile</a>
            {#if $isAdmin}
                <a href="/admin" class="hover:text-indigo-400">Admin Dashboard</a>
            {/if}
        </div>

        <!-- Logout Button (Always Visible) -->
        {#if $currentUser}
            <button
                    on:click={handleLogout}
                    class="bg-red-600 px-4 py-2 rounded hover:bg-red-700"
            >
                Logout
            </button>
        {:else}
            <a href="/login" class="bg-indigo-600 px-4 py-2 rounded hover:bg-indigo-700">Login</a>
        {/if}
    </div>

    <!-- Mobile Menu -->
    <div
            class={`md:hidden ${isMenuOpen ? 'block' : 'hidden'} bg-gray-800 text-center`}
    >
        <a href="/" class="block text-sm font-medium text-gray-300 hover:bg-gray-700 px-4 py-2">
            Home
        </a>
        <a href="/user/profile" class="block text-sm font-medium text-gray-300 hover:bg-gray-700 px-4 py-2">
            Profile
        </a>
        {#if $isAdmin}
            <a
                    href="/admin"
                    class="block text-sm font-medium text-gray-300 hover:bg-gray-700 px-4 py-2"
            >
                Admin Dashboard
            </a>
        {/if}
    </div>
</nav>