<script lang="ts">
    import {goto} from '$app/navigation';
    import {logout} from "../../../api_adapter/apiAdapter";
    import {authStore, isAdmin} from "../../../stores/auth";
</script>

<div class="min-h-screen bg-gray-100 flex items-center justify-center">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-sm w-full">
        <h2 class="text-3xl font-semibold text-center text-gray-800 mb-6">Welcome Back, {$authStore?.user?.name}!</h2>
        <p class="text-center text-gray-600 mb-6">You're now logged in. Enjoy your time here!</p>

        <div class="flex justify-center mb-6">
            <img
                    src="/pepe.png"
                    alt="User Avatar"
                    class="w-32 h-32 rounded-full border-4 border-indigo-600"
            />
        </div>

        {#if $isAdmin}
            <div class="mb-6 text-center">
                <button
                        on:click={() => goto('/admin')}
                        class="py-3 px-6 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
                >
                    Go to Admin Dashboard
                </button>
            </div>
        {/if}

        <div class="mb-6">
            <h3 class="text-xl font-medium text-gray-800 text-center mb-4">Your Roles:</h3>
            <p class="text-gray-700 text-sm text-center">
                {#if $authStore?.roles}
                    {#each $authStore.roles as role, i}
                        {role["authority"]}
                        {#if i < $authStore.roles.length - 1},{/if}
                    {/each}
                {/if}

            </p>
        </div>

        <div class="text-center mb-6">
            <button
                    on:click={logout}
                    class="py-3 px-6 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
            >
                Log Out
            </button>
        </div>

        <div class="text-center text-gray-600 text-sm">
            <a href="/user/profile" class="text-indigo-600 hover:text-indigo-800">Go to Profile</a>
        </div>
    </div>
</div>