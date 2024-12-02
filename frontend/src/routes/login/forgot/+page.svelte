<script lang="ts">
    import {type ResetPasswordRequest, UserControllerApi} from "../../../api_src"; // Import the generated API
    import {CheckCircle, ExclamationTriangle, Icon} from "svelte-hero-icons";

    let email = ''; // Bind the email input value
    let requestSuccessMsg: string | null = null;
    let requestErrorMsg: string | null = null;

    async function handleSubmit() {
        // create new ResetPasswordRequest
        const resetPasswordRequest: ResetPasswordRequest = {
            email: email
        };

        new UserControllerApi().resetPassword(resetPasswordRequest)
            .then(response => {
                console.log("sent, received:" + response);
                requestSuccessMsg = response;
            })
            .catch(error => {
                console.error("Error during password reset:", error);
                requestErrorMsg = "An error occurred. Please try again.";
            });
    }
</script>

<div class="min-h-screen bg-gray-100 flex items-center justify-center">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-sm w-full">
        <h2 class="text-2xl font-semibold text-center text-gray-800 mb-6">Forgot Password</h2>
        <p class="text-center text-gray-600 mb-6">Enter your email address and we will send you a link to reset your
            password.</p>
        <form on:submit|preventDefault={handleSubmit}>
            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700">Email Address</label>
                <input
                        id="email"
                        type="email"
                        bind:value={email}
                        placeholder="Enter your email"
                        class="mt-2 p-3 w-full border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500"
                        required
                />
            </div>

            <div class="flex items-center justify-between">
                <button
                        type="submit"
                        class="w-full py-3 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-200"
                >
                    Send Reset Link
                </button>
            </div>

            <div class="mt-4 text-center">
                <a href="/login" class="text-sm text-indigo-600 hover:text-indigo-800">Remember your password? Log
                    in</a>
            </div>

            {#if requestSuccessMsg}
                <div class="flex items-center p-2 mt-4 text-sm text-green-800 rounded-lg bg-green-50"
                     role="alert">
                    <Icon src={CheckCircle} class="mr-3 w-12 h-12"/>
                    <div>
                        {requestSuccessMsg}
                    </div>
                </div>
            {/if}
            {#if requestErrorMsg}
                <div class="flex items-center p-2 mt-4 text-sm text-red-800 rounded-lg bg-red-50"
                     role="alert">
                    <Icon src={ExclamationTriangle} class="mr-3 w-12 h-12"/>
                    <div>
                        {requestErrorMsg}
                    </div>
                </div>
            {/if}
        </form>
    </div>
</div>