<script lang="ts">
    import {onMount} from "svelte";
    import {
        type Allergen,
        AllergenControllerApi,
        type Allergy,
        AllergyControllerApi,
        type AssignAllergensRequest,
        type CreateAllergenRequest,
        type CreateAllergyRequest
    } from "../../api_src";

    // State Variables
    let allergies: Allergy[] = [];
    let allergens: Allergen[] = [];
    let selectedAllergyId: number | null = null;
    let selectedAllergy: Allergy | null = null;
    let allergensForSelectedAllergy: number[] = [];

    let newAllergyName = "";
    let newAllergenName = "";

    // API Controllers
    const allergyApi = new AllergyControllerApi();
    const allergenApi = new AllergenControllerApi();

    // Fetch Data on Initialization
    onMount(() => {
        getAllergies();
        getAllAllergens();
    });

    // Update selectedAllergy and allergensForSelectedAllergy when selectedAllergyId changes
    $: if (selectedAllergyId !== null) {
        selectedAllergy = allergies.find((allergy) => allergy.id === selectedAllergyId) || null;
        allergensForSelectedAllergy = selectedAllergy?.allergens?.map((allergen) => allergen.id!) || [];
    }

    // API Requests
    async function getAllergies() {
        try {
            const response = await allergyApi.getAllAllergies({credentials: "include"});
            allergies = response;
            console.log("Allergies retrieved successfully:", response);
        } catch (error) {
            console.error("Error getting allergies:", error);
        }
    }

    async function getAllAllergens() {
        try {
            const response = await allergenApi.getAllergens({credentials: "include"});
            allergens = response;
            console.log("Allergens retrieved successfully:", response);
        } catch (error) {
            console.error("Error getting allergens:", error);
        }
    }

    // Create Functions
    async function createAllergy() {
        if (!newAllergyName) return;

        const request: CreateAllergyRequest = {allergy: {name: newAllergyName}};
        try {
            const response = await allergyApi.createAllergy(request, {credentials: "include"});
            allergies = [...allergies, response];
            console.log("Allergy created successfully:", response);
        } catch (error) {
            console.error("Error creating allergy:", error);
        }
    }

    async function createAllergen() {
        if (!newAllergenName) return;

        const request: CreateAllergenRequest = {name: newAllergenName};
        try {
            const response = await allergenApi.createAllergen(request, {credentials: "include"});
            allergens = [...allergens, response];
            console.log("Allergen created successfully:", response);
        } catch (error) {
            console.error("Error creating allergen:", error);
        }
    }

    async function assignAllergens() {
        if (selectedAllergyId === null) {
            console.error("No allergy selected!");
            return;
        }

        // Find allergen objects from selected IDs
        const selectedAllergenObjects = allergens.filter((allergen) =>
            allergensForSelectedAllergy.includes(allergen.id!)
        );

        const request: AssignAllergensRequest = {
            allergyId: selectedAllergyId,
            allergen: selectedAllergenObjects,
        };

        try {
            const response = await allergyApi.assignAllergens(request, {credentials: "include"});
            console.log("Allergens assigned successfully:", response);
        } catch (error) {
            console.error("Error assigning allergens:", error);
        }
    }
</script>

<!-- spacer -->
<div class="h-12"></div>

<div class="max-w-3xl mx-auto p-6 space-y-6 bg-white rounded-lg shadow-md mt-8">
    <!-- Create Allergy and Create Allergen Section -->
    <div class="flex space-x-6">
        <!-- Create Allergy Section -->
        <div class="flex-1">
            <h2 class="text-2xl font-semibold text-gray-800 mb-4">Create Allergy</h2>
            <div class="space-y-4">
                <div>
                    <label for="allergyName" class="block text-sm font-medium text-gray-600">Allergy Name</label>
                    <input id="allergyName" type="text" bind:value={newAllergyName}
                           class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                           placeholder="Enter allergy name"/>
                </div>
                <button on:click={createAllergy}
                        class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition">
                    Create Allergy
                </button>
            </div>
        </div>

        <!-- Create Allergen Section -->
        <div class="flex-1 border-l border-gray-300 pl-6">
            <h2 class="text-2xl font-semibold text-gray-800 mb-4">Create Allergen</h2>
            <div class="space-y-4">
                <div>
                    <label for="allergenName" class="block text-sm font-medium text-gray-600">Allergen Name</label>
                    <input id="allergenName" type="text" bind:value={newAllergenName}
                           class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                           placeholder="Enter allergen name"/>
                </div>
                <button on:click={createAllergen}
                        class="px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-600 transition">
                    Create Allergen
                </button>
            </div>
        </div>
    </div>

    <div class="border-t border-gray-300 pt-6">
        <!-- Assign Allergen to Allergy Section -->
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Assign Allergen to Allergy</h2>
        <div class="space-y-4">
            <!-- Select Allergy -->
            <div>
                <label for="selectAllergy" class="block text-sm font-medium text-gray-700 mb-1">Select Allergy</label>
                <div class="relative">
                    <select
                            id="selectAllergy"
                            bind:value={selectedAllergyId}
                            class="mt-1 p-3 block w-full appearance-none bg-white border border-gray-300 rounded-lg shadow-md text-gray-800 focus:ring-2 focus:ring-blue-500 focus:outline-none"
                    >
                        <option value="" disabled selected class="text-gray-400">-- Select an allergy --</option>
                        {#each allergies as allergy}
                            <option value={allergy.id} class="text-gray-900">{allergy.name}</option>
                        {/each}
                    </select>
                    <!-- Down arrow icon -->
                    <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                        <svg class="h-5 w-5 text-gray-500" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                             fill="currentColor">
                            <path fill-rule="evenodd"
                                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                                  clip-rule="evenodd"/>
                        </svg>
                    </div>
                </div>
            </div>

            <!-- Select Allergens (Multiple) -->
            <div>
                <label for="allergenSelector" class="block text-sm font-medium text-gray-600">Select Allergens</label>
                <div
                        id="allergenSelector"
                        class="space-y-2 mt-2 max-h-64 overflow-y-scroll border border-gray-300 p-2 rounded-md"
                >
                    {#each allergens as allergen}
                        <div class="flex items-center">
                            <input
                                    id={`allergen-${allergen.id}`}
                                    type="checkbox"
                                    bind:group={allergensForSelectedAllergy}
                                    value={allergen.id}
                                    class="mr-2"
                            />
                            <label for={`allergen-${allergen.id}`} class="text-gray-700">{allergen.name}</label>
                        </div>
                    {/each}
                </div>
            </div>

            <button on:click={assignAllergens}
                    class="px-4 py-2 bg-yellow-500 text-white rounded-md hover:bg-yellow-600 transition">
                Assign Allergens
            </button>
        </div>
    </div>
</div>