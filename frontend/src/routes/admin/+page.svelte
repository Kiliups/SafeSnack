<script lang="ts">
    // Svelte store for allergies and allergens data
    import {
        type Allergen,
        AllergenControllerApi,
        type Allergy,
        AllergyControllerApi,
        type AssignAllergensRequest,
        type CreateAllergenRequest,
        type CreateAllergyRequest
    } from "../../api_src";

    // Svelte store for allergies and allergens data
    let allergies: Allergy[] = [];
    let allergens: Allergen[] = [];

    let selectedAllergyId: number | null = null;
    let selectedAllergy: Allergy | null = null; // Store the selected allergy to access its allergens

    let newAllergyName: string = "";
    let newAllergenName: string = "";

    let allergyApi: AllergyControllerApi = new AllergyControllerApi();
    let allergenApi: AllergenControllerApi = new AllergenControllerApi();

    // Fetch allergies and allergens
    $: {
        getAllergies();
        getAllAllergens();
    }

    function getAllergies() {
        allergyApi.getAllAllergies({credentials: 'include'})
            .then((response) => {
                console.log("Allergies retrieved successfully: ", response);
                allergies = response;
            })
            .catch((error) => {
                console.error("Error getting allergies: ", error);
            });
    }

    function getAllAllergens() {
        allergenApi.getAllergens({credentials: 'include'})
            .then((response) => {
                console.log("Allergens retrieved successfully: ", response);
                allergens = response;
            })
            .catch((error) => {
                console.error("Error getting allergens: ", error);
            });
    }

    // Function to handle creating an allergy
    function createAllergy() {
        let request: CreateAllergyRequest = {
            allergy: {name: newAllergyName}
        };

        allergyApi.createAllergy(request, {credentials: 'include'})
            .then((response) => {
                console.log("Allergy created successfully: ", response);
                allergies = [...allergies, response];
            })
            .catch((error) => {
                console.error("Error creating allergy: ", error);
            });
    }

    // Function to handle creating an allergen
    function createAllergen() {
        let request: CreateAllergenRequest = {
            name: newAllergenName
        };

        allergenApi.createAllergen(request, {credentials: 'include'})
            .then((response) => {
                console.log("Allergen created successfully: ", response);
                allergens = [...allergens, response];
            })
            .catch((error) => {
                console.error("Error creating allergen: ", error);
            });
    }

    function assignAllergens() {
        if (selectedAllergyId === null) {
            console.error("No allergy selected!");
            return;
        }

        // Convert allergen ids back to allergen objects before sending the request
        let selectedAllergenObjects = allergens.filter(allergen =>
            allergensForSelectedAllergy.includes(allergen.id)
        );

        let request: AssignAllergensRequest = {
            allergyId: selectedAllergyId,
            allergen: selectedAllergenObjects // Pass the array of allergen objects
        };

        allergyApi.assignAllergens(request, {credentials: 'include'})
            .then((response) => {
                console.log("Allergens assigned successfully: ", response);
            })
            .catch((error) => {
                console.error("Error assigning allergens: ", error);
            });
    }

    // Update selected allergy when the selection changes
    $: if (selectedAllergyId !== null) {
        selectedAllergy = allergies.find(allergy => allergy.id === selectedAllergyId) || null;
    }


    // Reactive statement to update allergensForSelectedAllergy when selectedAllergy changes -->
    $: allergensForSelectedAllergy = selectedAllergy ? selectedAllergy!.allergens!.map(allergen => allergen.id) : [];


</script>

<div class="max-w-3xl mx-auto p-6 space-y-6 bg-white rounded-lg shadow-md">
    <!-- Create Allergy Section -->
    <div>
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

    <div class="border-t border-gray-300 pt-6">
        <!-- Create Allergen Section -->
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

    <div class="border-t border-gray-300 pt-6">
        <!-- Assign Allergen to Allergy Section -->
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Assign Allergen to Allergy</h2>
        <div class="space-y-4">
            <!-- Select Allergy -->
            <div>
                <label for="selectAllergy" class="block text-sm font-medium text-gray-600">Select Allergy</label>
                <select id="selectAllergy" bind:value={selectedAllergyId}
                        class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    <option value="" disabled selected>Select an allergy</option>
                    {#each allergies as allergy}
                        <option value={allergy.id}>{allergy.name}</option>
                    {/each}
                </select>
            </div>

            <!-- Select Allergens (Multiple) -->
            <div>
                <label for="allergenSelector" class="block text-sm font-medium text-gray-600">Select Allergens</label>
                <div id="allergenSelector" class="space-y-2 mt-2">
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