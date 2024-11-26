<script>
    import {goto} from '$app/navigation';
    import {authStore} from "../../../stores/auth";
    import {logout} from "../../../api_adapter/apiAdapter";

    function handleEditProfile() {
        // TODO: Implement edit profile functionality
        alert("Edit Profile clicked!");
    }

    function handleImgClick() {
        goto('/user/hello')
    }
</script>

<div class="min-h-screen bg-gray-100 flex items-center justify-center py-12">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-md w-full">
        <!-- Profile Header -->
        <div class="flex items-center justify-center mb-6">
            <button
                    type="button"
                    on:click={handleImgClick}
                    class="p-0 border-none focus:outline-none"
            >
                <img
                        src="../../../../img/pepe.png"
                        alt="User Avatar"
                        class="w-32 h-32 rounded-full border-4 border-indigo-600"
                />
            </button>
        </div>

        <h2 class="text-3xl font-semibold text-center text-gray-800 mb-4">{$authStore?.user?.name}</h2>
        <p class="text-center text-gray-600 mb-4">{$authStore?.user?.email}</p>

        <!-- Buttons -->
        <div class="flex justify-center gap-4">
            <button
                    on:click={handleEditProfile}
                    class="py-3 px-6 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
            >
                Edit Profile
            </button>

            <button
                    on:click={logout}
                    class="py-3 px-6 bg-red-600 text-white rounded-md hover:bg-red-700 focus:ring-4 focus:ring-red-200"
            >
                Log Out
            </button>
        </div>
    </div>
</div>