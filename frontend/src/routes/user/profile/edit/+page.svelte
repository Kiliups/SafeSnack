<script lang="ts">
    import {authStore} from "../../../../stores/auth";
    import type {RemoveAllergyRequest} from "../../../../api_src";
    import {type Allergy, AllergyControllerApi, type CreateAllergyRequest} from "../../../../api_src";

    // car to store allergies
    let allAllergies: Allergy[] = [];
    let userAllergies: Allergy[] = [];

    // call load functions on page load
    $: {
        getUserAllergies();
        getAllAllergies();
    }

    // get user allergies from backend
    async function getUserAllergies() {
        new AllergyControllerApi().getAllergy({credentials: 'include'}).then((response) => {
            console.log("Done:" + response);
            userAllergies = response;
        }).catch((error) => {
            console.error("Error getting allergies:" + error);
        });
    }

    function refreshAllergies() {
        getUserAllergies();
        getAllAllergies();
    }

    // get all allergies from backend
    async function getAllAllergies() {
        new AllergyControllerApi().getAllAllergies({credentials: 'include'}).then((response) => {
            console.log("Done:" + response);
            // remove user allergies from all allergies
            allAllergies = response.filter((allergy) => !userAllergies.some((userAllergy) => userAllergy.id === allergy.id));
        }).catch((error) => {
            console.error("Error getting allergies:" + error);
        });
    }

    async function takeAllergy(allergy: Allergy) {

        // create CreateAllergyRequest request
        const request: CreateAllergyRequest = {
            allergy: allergy
        }

        new AllergyControllerApi().assignAllergy(request, {credentials: 'include'})
            .then((response) => {
                console.log("Done:" + response);
                refreshAllergies();
            })
            .catch((error) => {
                console.error("Error taking allergy:" + error);
            });
    }

    // remove allergy from user
    async function removeAllergy(allergy: Allergy) {
        // create remove request
        const request: RemoveAllergyRequest = {
            allergy: allergy
        }

        new AllergyControllerApi().removeAllergy(request, {credentials: 'include'})
            .then((response) => {
                console.log("Done:" + response);
                refreshAllergies();
            })
            .catch((error) => {
                console.error("Error removing allergy:" + error);
            });
    }


</script>

<div class="flex flex-col justify-center items-center w-full h-full p-6 bg-gray-100">
    <h1 class="text-3xl font-semibold text-gray-800 mb-4">Hello {$authStore?.user?.name}!</h1>


    <!-- User Allergies Section -->
    <h2 class="text-2xl font-medium text-gray-700 mt-5 mb-2">Your Allergies:</h2>
    <ul class="w-full max-w-lg space-y-2">
        {#each userAllergies as allergy}
            <li class="flex items-center justify-between bg-white p-3 rounded-lg shadow-md hover:bg-gray-50">
                <span class="text-lg text-gray-800">{allergy.name}</span>
                <button on:click={() => removeAllergy(allergy)}
                        class="px-4 py-2 bg-red-500 text-white rounded hover:bg-blue-600 transition duration-300 ease-in-out">
                    Remove
                </button>
            </li>
        {/each}
    </ul>

    <!-- Black separator line -->
    <div class="w-1/2 h-1 bg-gray-400 my-6 shadow-lg"></div>

    <!-- Available Allergies Section -->
    <h2 class="text-2xl font-medium text-gray-700 mt-5 mb-2">Available Allergies:</h2>
    <ul class="w-full max-w-lg space-y-2">
        {#each allAllergies as allergy}
            <li class="flex items-center justify-between bg-white p-3 rounded-lg shadow-md hover:bg-gray-50">
                <span class="text-lg text-gray-800">{allergy.name}</span>
                <button on:click={() => takeAllergy(allergy)}
                        class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-300 ease-in-out">
                    Take
                </button>
            </li>
        {/each}
    </ul>
</div>