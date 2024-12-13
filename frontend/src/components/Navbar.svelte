<script lang="ts">
    import {authStore, isAdmin} from "../stores/auth";
    import {logout} from "../api_adapter/apiAdapter";

    let isMenuOpen = false; // State for mobile menu

    function toggleMenu() {
        isMenuOpen = !isMenuOpen; // Toggle the menu visibility
    }
</script>

<nav class="bg-ground text-primary fixed top-0 left-0 w-full z-50 shadow-lg">
    <div class="container mx-auto px-4 flex justify-between items-center h-16">
        <!-- Logo -->
        <a href="/" class="text-xl font-bold hover:text-indigo-300">SafeSnack</a>

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
            <a href="/" class="hover:text-indigo-300">Home</a>
            <a href="/user/profile" class="hover:text-indigo-300">Profile</a>
            <a href="/restaurant" class="hover:text-indigo-300">Restaurant</a>
            {#if $isAdmin}
                <a href="/admin" class="hover:text-indigo-300">Admin Dashboard</a>
            {/if}
        </div>

        <!-- Logout Button (Always Visible) -->
        {#if $authStore?.user}
            <button
                    on:click={logout}
                    class="bg-error px-4 py-2 rounded text-ground"
            >
                Logout
            </button>
        {:else}
            <a href="/login" class="bg-accent px-4 py-2 rounded hover:bg-indigo-300 text-ground">Login</a>
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
        <a href="/restaurant" class="block text-sm font-medium text-gray-300 hover:bg-gray-700 px-4 py-2">
            Restaurant
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