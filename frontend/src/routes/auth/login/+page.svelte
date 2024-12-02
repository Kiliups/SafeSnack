<script lang="ts">
    import {enhance} from "$app/forms";
    import type {SubmitFunction} from "@sveltejs/kit";
    import {goto} from "$app/navigation";

    let username = '';
    let password = '';
    let errorMessage: string | undefined = '';

    const handleSubmit: SubmitFunction = ({cancel}) => {
        // Optional: client-side validation
        if (!username || !password) {
            cancel();
            return;
        }

        return async ({result, update}) => {
            // Handle different result types
            if (result.type === 'success') {
                await update();
                await goto("/user/hello");
            } else if (result.type === 'failure') {
                // Handle login errors
                errorMessage = result.data?.message ?? 'An unexpected error occurred';
                password = '';
                console.error(result.data);
            }
        };
    };

</script>


<div class="min-h-screen bg-ground flex items-center justify-center py-12">
    <div class="bg-primary text-ground p-8 rounded-lg shadow-lg max-w-sm w-full">
        <h2 class="text-3xl font-semibold text-center mb-6">Login</h2>

        <!-- Login form -->
        <form method="POST" action="?/login" use:enhance={handleSubmit} class="space-y-6">
            <div>
                <label for="email" class="block text-sm font-medium">Email or Username</label>
                <input
                        id="email"
                        name="username"
                        type="text"
                        bind:value={username}
                        class="mt-2 p-3 w-full border rounded-md focus:outline-none focus:ring-2 focus:ring-accent"
                        placeholder="Enter your email or username"
                        required
                />
            </div>

            <div>
                <label for="password" class="block text-sm font-medium ">Password</label>
                <input
                        id="password"
                        name="password"
                        type="password"
                        bind:value={password}
                        class="mt-2 p-3 w-full border rounded-md focus:outline-none focus:ring-2 focus:ring-accent"
                        placeholder="Enter your password"
                        required
                />
            </div>

            <!-- Error message display -->
            {#if errorMessage}
                <div class="text-error">{errorMessage}</div>
            {/if}

            <div class="flex justify-center">
                <button
                        type="submit"
                        class="w-full py-3 px-6 bg-accent hover:bg-indigo-300 text-ground rounded-md "
                >
                    Login
                </button>
            </div>
        </form>

        <div class="text-center text-sm mt-4">
            <a href="/login/forgot" class="text-ground hover:text-accent">Forgot your password?</a>
        </div>
    </div>
</div>