<script lang="ts">
    import {enhance} from "$app/forms";
    import type {SubmitFunction} from "@sveltejs/kit";
    import {goto} from "$app/navigation";
    import Input from "../../../components/Input.svelte";
    import {CITY, DESCRIPTION, EMAIL, NAME, PASSWORD, STREET, USERNAME, ZIP} from "../../../utils/const";

    let username = $state<string | undefined>('');
    let password = $state<string | undefined>('');
    let email = $state<string | undefined>('');
    let name = $state<string | undefined>('');
    let street = $state<string | undefined>('');
    let city = $state<string | undefined>('');
    let zip = $state<string | undefined>('');
    let description = $state<string | undefined>('');
    let errorMessage = $state<string | undefined>(undefined);

    let isRestaurant = $state(false);

    const handleSubmit: SubmitFunction = ({cancel}) => {
        console.log("username", username);
        console.log(username, password, email, name, street, city, zip, description);
        // Optional: client-side validation
        if (!username || !password) {
            cancel();
            return;
        }

        return async ({result, update}) => {
            // Handle different result types
            if (result.type === 'success') {
                await update();
                await goto("/login");
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
        <form method="POST" action="?/signup" use:enhance={handleSubmit} class="space-y-6">
            <button onclick={()=>{isRestaurant=!isRestaurant}}
                    class="justify-center w-full py-3 px-6 bg-accent hover:bg-indigo-300 text-ground rounded-md">{isRestaurant ? "Restaurant" : "User"}</button>
            <Input label="Username" id={USERNAME} bind:value={username} placeHolder="Enter your username"
                   required={true}/>
            <Input label="Password" id={PASSWORD} bind:value={password} placeHolder="Enter your password"
                   required={true} type="password"/>
            <Input label="Name" id={NAME} bind:value={name} placeHolder="Enter your name" required={true}/>
            <Input label="Email" id={EMAIL} bind:value={email} placeHolder="Enter your email" required={true}
                   type="email"/>
            {#if isRestaurant}
                <Input label="Straße und Hausnummer" id={STREET} bind:value={street}
                       placeHolder="Enter your Street and House Number" required={true}/>
                <Input label="Stadt" id={CITY} bind:value={city}
                       placeHolder="Enter your City" required={true}/>
                <Input label="PLZ" id={ZIP} bind:value={zip}
                       placeHolder="Enter your Zip code" required={true}/>
                <Input label="Beschreibung" id={DESCRIPTION} bind:value={description}
                       placeHolder="Enter your description" required={true}/>
            {/if}

            {#if errorMessage}
                <div class="text-error">{errorMessage}</div>
            {/if}

            <div class="flex justify-center">
                <button
                        type="submit"
                        class="w-full py-3 px-6 bg-accent hover:bg-indigo-300 text-ground rounded-md "
                >
                    Sign Up
                </button>
            </div>
        </form>
    </div>
</div>