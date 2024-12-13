<script lang="ts">
    import {authStore} from "../../../../stores/auth";
    import {onMount} from "svelte";
    import {
        type Allergy,
        AllergyControllerApi,
        type ChangeUserPasswordRequest,
        type CreateAllergyRequest,
        type RemoveAllergyRequest,
        type UpdateUserRequest,
        UserControllerApi
    } from "../../../../api_src";
    import ConfirmPopUp from "../../../../components/ConfirmPopUp.svelte";
    import {goto} from "$app/navigation";

    let allAllergies: Allergy[] = [];
    let userAllergies: Allergy[] = [];

    // User details fields
    let email = '';
    let name = '';
    let oldPassword = '';
    let newPassword = '';
    let searchQuery = '';
    let passwordErrorMsg = '';
    let showPopup = false;

    onMount(() => {
        if ($authStore!.user!.type === 'restaurant') {
            goto('/restaurant/edit');
        }
        refreshAllergies();
    });

    $: {
        email = $authStore?.user?.email || '';
        name = $authStore?.user?.name || '';
    }

    function refreshAllergies() {
        getUserAllergies();
        getAllAllergies();
    }

    function handleResetClick() {
        showPopup = true;
    }

    function handleCancel() {
        showPopup = false;
    }

    function handlePasswordReset() {
        // Check if the password fields are empty
        if (!oldPassword || !newPassword) {
            console.error("Password fields cannot be empty.");
            //requestErrorMsg = "Password fields cannot be empty.";
            showPopup = false;
            return;
        }

        // create new ResetPasswordRequest
        const changePasswordRequest: ChangeUserPasswordRequest = {
            passwordChangeContainer: {
                oldPassword: oldPassword,
                newPassword: newPassword
            }
        };

        new UserControllerApi().changeUserPassword(changePasswordRequest, {credentials: 'include'})
            .then(response => {
                console.log("sent, received:" + response);
                passwordErrorMsg = '';
                showPopup = false;
                oldPassword = '';
                newPassword = '';
            })
            .catch(error => {
                console.error("Error during password reset:", error);
                passwordErrorMsg = "An error occurred. Please try again.";
                showPopup = false;
            });
    }

    async function getUserAllergies() {
        try {
            userAllergies = await new AllergyControllerApi().getAllergy({credentials: 'include'});
        } catch (error) {
            console.error("Error getting allergies:", error);
        }
    }

    async function getAllAllergies() {
        try {
            const response = await new AllergyControllerApi().getAllAllergies({credentials: 'include'});
            allAllergies = response.filter((allergy) => !userAllergies.some((userAllergy) => userAllergy.id === allergy.id));
        } catch (error) {
            console.error("Error getting allergies:", error);
        }
    }

    async function takeAllergy(allergy: Allergy) {
        const request: CreateAllergyRequest = {allergy};
        try {
            await new AllergyControllerApi().assignAllergy(request, {credentials: 'include'});
            refreshAllergies();
        } catch (error) {
            console.error("Error taking allergy:", error);
        }
    }

    async function removeAllergy(allergy: Allergy) {
        const request: RemoveAllergyRequest = {allergy};
        try {
            await new AllergyControllerApi().removeAllergy(request, {credentials: 'include'});
            refreshAllergies();
        } catch (error) {
            console.error("Error removing allergy:", error);
        }
    }

    function updateUserDetails() {
        const request: UpdateUserRequest = {
            userMeta: {
                type: 'user_meta',
                email: email,
                name: name,
                id: $authStore?.user?.id
            }
        };

        new UserControllerApi().updateUser(request, {credentials: 'include'})
            .then(response => {
                console.log("User details updated:", response);
            })
            .catch(error => {
                console.error("Error updating user details:", error);
            });
    }
</script>

<div class="flex flex-col items-center w-full h-100 p-6 bg-gray-100 mt-12">
    <div class="max-w-5xl w-full bg-white rounded-lg shadow-lg p-6">
        <!-- Edit Profile Header -->
        <h1 class="text-3xl font-semibold text-gray-800 mb-6">Edit Profile</h1>

        <!-- Profile Details and Password Reset Section -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- User Info Section -->
            <div class="space-y-4">
                <h2 class="text-xl font-medium text-gray-700">User Information</h2>
                <div>
                    <label for="name" class="block text-sm font-medium text-gray-600">Name</label>
                    <input id="name" type="text" bind:value={name}
                           class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"/>
                </div>
                <div>
                    <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
                    <input id="email" type="email" bind:value={email}
                           class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"/>
                </div>
                <button on:click={updateUserDetails}
                        class="w-full py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition">
                    Save Changes
                </button>
            </div>

            <!-- Password Reset Section -->
            <div class="space-y-4">
                <h2 class="text-xl font-medium text-gray-700">Reset Password</h2>

                <!-- Old Password Input -->
                <div>
                    <label for="oldPassword" class="block text-sm font-medium text-gray-700 mb-1">
                        Old Password
                    </label>
                    <input
                            id="oldPassword"
                            type="password"
                            bind:value={oldPassword}
                            class="w-full px-4 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500
                   {passwordErrorMsg ? 'border-red-500' : 'border-gray-300'}"
                    />
                </div>

                <!-- New Password Input -->
                <div>
                    <label for="newPassword" class="block text-sm font-medium text-gray-700 mb-1">
                        New Password
                    </label>
                    <input
                            id="newPassword"
                            type="password"
                            bind:value={newPassword}
                            class="w-full px-4 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500
                   {passwordErrorMsg ? 'border-red-500' : 'border-gray-300'}"
                    />
                </div>

                <!-- Error Message -->
                {#if passwordErrorMsg}
                    <p class="text-sm text-red-500">{passwordErrorMsg}</p>
                {/if}

                <!-- Reset Button -->
                <div class="text-right">
                    <button
                            on:click={handleResetClick}
                            class="px-5 py-2 bg-blue-500 text-white font-medium rounded-md hover:bg-blue-600 transition"
                    >
                        Reset Password
                    </button>
                </div>
            </div>
        </div>

        <!-- Popup -->
        {#if showPopup}
            <ConfirmPopUp
                    title="Reset Password"
                    message="Are you sure you want to reset your password?"
                    confirmLabel="Reset"
                    cancelLabel="Cancel"
                    onConfirm={handlePasswordReset}
                    onCancel={handleCancel}
            />
        {/if}
    </div>

    <div class="max-w-5xl w-full bg-white rounded-lg shadow-lg p-6 mt-3">
        <h1 class="text-3xl font-semibold text-gray-800 mb-6">Allergies</h1>

        <!-- Allergies Section -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- User Allergies Section -->
            <div class="space-y-4">
                <h2 class="text-xl font-medium text-gray-700">Your Allergies</h2>
                <!-- Allergies List -->
                <ul class="divide-y divide-gray-200 mt-0">
                    {#each userAllergies as allergy}
                        <li class="flex justify-between items-center m-2 p-1.5 rounded-md border-2 bg-gray-100">
                            <span class="text-gray-700 text-lg">{allergy.name}</span>
                            <button
                                    on:click={() => removeAllergy(allergy)}
                                    class="px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 transition"
                            >
                                Remove
                            </button>
                        </li>
                    {/each}
                </ul>
            </div>

            <!-- Available Allergies Section -->
            <div class="space-y-4">
                <h2 class="text-xl font-medium text-gray-700">Available Allergies</h2>

                <!-- Search Bar -->
                <input
                        type="text"
                        bind:value={searchQuery}
                        placeholder="Search allergies..."
                        class=" w-full p-3 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                />

                <!-- Allergies List -->
                <ul class="divide-y divide-gray-200">
                    {#each allAllergies.filter((allergy) =>
                        allergy.name?.toLowerCase().includes(searchQuery.toLowerCase())
                    ) as allergy}
                        <li class="flex justify-between items-center my-2 p-1.5 rounded-md border-2 bg-gray-100">
                            <span class="text-gray-700 text-lg">{allergy.name}</span>
                            <button
                                    on:click={() => takeAllergy(allergy)}
                                    class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition"
                            >
                                Take
                            </button>
                        </li>
                    {/each}
                </ul>

            </div>

        </div>
    </div>
</div>