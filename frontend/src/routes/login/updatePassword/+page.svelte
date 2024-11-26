<script lang="ts">
    import {onMount} from 'svelte';
    import {type SavePasswordRequest, UserControllerApi} from "../../../api_src"; // Import the generated API

    let token = '';
    let newPassword = '';
    let confirmPassword = '';
    let errorMessage = '';
    let successMessage = '';

    // Retrieve the token from the URL
    onMount(() => {
        token = new URLSearchParams(window.location.search).get('token') || '';
        // check if token is null or empty
        if (!token) {
            errorMessage = 'Invalid or missing token.';
        }
    });

    // Function to handle the password reset
    const handleResetPassword = () => {
        // Check if the passwords match
        if (newPassword !== confirmPassword) {
            errorMessage = 'Passwords do not match.';
            return;
        }

        // Create a new SavePasswordRequest
        const savePasswordRequest: SavePasswordRequest = {
            // new PasswordResetContainer
            passwordResetContainer: {
                token: token,
                newPassword: newPassword
            }
        };

        // Create a new UserControllerApi
        const userControllerApi = new UserControllerApi();

        // Send the request
        userControllerApi.savePassword(savePasswordRequest)
            .then(response => {
                console.log("sent, received:" + response);
                successMessage = 'Your password has been successfully reset.';
                errorMessage = ''; // Clear any previous error message
            })
            .catch(error => {
                console.error("Error during password reset:", error);
                errorMessage = 'An error occurred. Please try again.';
            });
    };
</script>

<div class="min-h-screen flex items-center justify-center bg-gray-50 p-6">
    <div class="max-w-md w-full bg-white p-8 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold text-center mb-6">Reset Your Password</h2>

        {#if errorMessage}
            <div class="bg-red-500 text-white p-2 mb-4 rounded">{errorMessage}</div>
        {/if}

        {#if successMessage}
            <div class="bg-green-500 text-white p-2 mb-4 rounded">{successMessage}</div>
        {/if}

        <form on:submit|preventDefault={handleResetPassword} class="space-y-4">
            <div>
                <label for="new-password" class="block text-sm font-medium text-gray-700">New Password</label>
                <input
                        id="new-password"
                        type="password"
                        bind:value={newPassword}
                        placeholder="Enter your new password"
                        class="mt-1 px-4 py-2 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                />
            </div>

            <div>
                <label for="confirm-password" class="block text-sm font-medium text-gray-700">Confirm Password</label>
                <input
                        id="confirm-password"
                        type="password"
                        bind:value={confirmPassword}
                        placeholder="Confirm your new password"
                        class="mt-1 px-4 py-2 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                />
            </div>

            <div class="flex justify-center">
                <button
                        type="submit"
                        class="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    Reset Password
                </button>
            </div>
        </form>
    </div>
</div>