<script>
    import {currentUser, currentUserRoles} from '../../../stores/user';
    import {goto} from '$app/navigation';
    import {onMount} from "svelte";

    onMount(() => {
        // Check if the user is logged in, if not, redirect to the login page
        if (!$currentUser) {
            goto('/login'); // Redirect to the login page
        }
    });

    function handleEditProfile() {
        // TODO: Implement edit profile functionality
        alert("Edit Profile clicked!");
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

<div class="min-h-screen bg-gray-100 flex items-center justify-center py-12">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-md w-full">
        <!-- Profile Header -->
        <div class="flex items-center justify-center mb-6">
            <img
                    src="../../../../img/pepe.png"
                    alt="User Avatar"
                    class="w-32 h-32 rounded-full border-4 border-indigo-600"
            />
        </div>

        <h2 class="text-3xl font-semibold text-center text-gray-800 mb-4">{$currentUser?.name}</h2>
        <p class="text-center text-gray-600 mb-4">{$currentUser?.email}</p>

        <!-- Buttons -->
        <div class="flex justify-center gap-4">
            <button
                    on:click={handleEditProfile}
                    class="py-3 px-6 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
            >
                Edit Profile
            </button>

            <button
                    on:click={handleLogout}
                    class="py-3 px-6 bg-red-600 text-white rounded-md hover:bg-red-700 focus:ring-4 focus:ring-red-200"
            >
                Log Out
            </button>
        </div>
    </div>
</div>