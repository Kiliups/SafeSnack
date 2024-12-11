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
    <!-- Create Menus (Future only restaurants) -->
    <div class="flex space-x-6">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Create Menu</h2>
        <div>
            <label for="menuName" class="block text-sm font-medium text-gray-600">Menu name</label>
            <input id="menuName" type="text" bind:value={newMenuName}
                   class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                   placeholder="Enter menu name"/>
        </div>
        <div>
            <label for="menuDescription" class="block text-sm font-medium text-gray-600">Menu description</label>
            <input id="menuDescription" type="text" bind:value={newMenuDescription}
                   class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                   placeholder="Enter menu description"/>
        </div>
        <button on:click={createMenu}
                class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition">
            Create Menu
        </button>
    </div>
</div>