<script lang="ts">
    import Input from "../../../components/Input.svelte";
    import {RestaurantControllerApi} from "../../../api_src";
    import {type Restaurant} from "../../../api_src/models/Restaurant";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/restaurant/[restaurantId]/$types";

    let {data}: { data: PageData } = $props();
    const restaurant = $state(data.userContainer?.user as Restaurant);

    const updateRestaurantDetails = () => {
        restaurant.type = "restaurant";
        new RestaurantControllerApi().updateRestaurant({restaurant: restaurant}, {credentials: 'include'})
            .then((response) => {
                console.log(response);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error updating restaurant:", error);
            });
    }
    let deleteRestaurant = () => {
        new RestaurantControllerApi().deleteRestaurant({id: restaurant.id!}, {credentials: 'include'})
            .then((response) => {
                console.log(response);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error deleting restaurant:", error);
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
            {#if restaurant}
                <div class="space-y-4">
                    <h2 class="text-xl font-medium text-gray-700">Restaurant Information</h2>
                    <Input divClass="w-full" classLabel="block text-sm font-medium text-gray-600" label="Name" id={3}
                           bind:value={restaurant.name} placeHolder="Enter your restaurant name" required={true}/>
                    <Input divClass="w-full" classLabel="block text-sm font-medium text-gray-600" label="Description"
                           id={3}
                           bind:value={restaurant.description} placeHolder="Enter your restaurant description"
                           required={true}/>
                    <Input divClass="w-full" classLabel="block text-sm font-medium text-gray-600" label="Street" id={3}
                           bind:value={restaurant.address.street} placeHolder="Enter your restaurant street"
                           required={true}/>
                    <Input divClass="w-full" classLabel="block text-sm font-medium text-gray-600" label="City" id={3}
                           bind:value={restaurant.address.city} placeHolder="Enter your restaurant city"
                           required={true}/>
                    <Input divClass="w-full" classLabel="block text-sm font-medium text-gray-600" label="Zip" id={3}
                           bind:value={restaurant.address.zip} placeHolder="Enter your restaurant zip"
                           required={true}/>


                    <button onclick={updateRestaurantDetails}
                            class="w-full py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition">
                        Save Changes
                    </button>

                    <!-- delete button -->
                    <button class="w-full py-2 bg-red-500 text-white rounded-md hover:bg-red-600 transition"
                            onclick={deleteRestaurant}>
                        Delete Account
                    </button>
                </div>
            {/if}
        </div>
    </div>
</div>