<script lang="ts">
    import type {PageData} from './$types';
    import Input from "../../../components/Input.svelte";
    import {type RestaurantRating, RestaurantRatingControllerApi} from "../../../api_src";

    let {data}: { data: PageData } = $props();
    let restaurantContainer = data.restaurantContainer;
    let restaurant = restaurantContainer.restaurant;
    let restaurantRatings = restaurantContainer.restaurantRatings;

    let newRatingNumber = $state<number>();
    let newRatingComment = $state<string>();
    console.log(data);

    function saveNewRating() {
        const newRating: RestaurantRating = {
            rating: newRatingNumber,
            comment: newRatingComment,
        };
        newRating.restaurant = restaurant;
        newRating.restaurant!.type = "restaurant";
        newRating.user = data.userContainer?.user;
        newRating.user!.type = "user_meta";
        console.log("new:", newRating);
        new RestaurantRatingControllerApi()
            .addRestaurantRating({restaurantRating: newRating}, {credentials: 'include'})
            .then((response) => {
                console.log(response);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error saving rating:", error);
            });
    }

    function updateRating(rating: RestaurantRating) {
        rating.restaurant!.type = "restaurant";
        rating.user!.type = "user_meta";
        new RestaurantRatingControllerApi()
            .updateRestaurantRating({restaurantRating: rating}, {credentials: 'include'})
            .then((response) => {
                console.log(response);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error saving rating:", error);
            });
    }

    function deleteRating(rating: RestaurantRating) {
        new RestaurantRatingControllerApi()
            .deleteRestaurantRating({id: rating.id!}, {credentials: 'include'})
            .then((response) => {
                console.log(response);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error deleting rating:", error);
            });
    }
</script>

<div class="h-full bg-gray-100 text-gray-900 mt-10">
    <div class="container mx-auto py-10">
        <h1 class="text-4xl font-bold text-center mb-8">{restaurant?.name}</h1>

        <div class="bg-white rounded-lg shadow-lg p-6">
            <h2 class="text-2xl font-semibold mb-4">Address</h2>
            <p class="text-gray-700">{restaurant?.address?.street},</p>
            <p class="text-gray-700">{restaurant?.address?.city},</p>
            <p class="text-gray-700">{restaurant?.address?.zip}</p>
        </div>

        <div class="bg-white rounded-lg shadow-lg p-6 mt-6">
            <h2 class="text-2xl font-semibold mb-4">Description</h2>
            <p class="text-gray-700">{restaurant?.description}</p>
        </div>

        <div class="bg-white rounded-lg shadow-lg p-6 mt-6">
            <h2 class="text-2xl font-semibold mb-4">Menu</h2>
            <ul class="list-disc list-inside text-gray-700">
            </ul>
        </div>

        {#if restaurantRatings}
            <div class="bg-white rounded-lg shadow-lg p-6 mt-6">
                <h2 class="text-2xl font-semibold mb-4">Ratings</h2>
                <ul class="list-disc list-inside text-gray-700">
                    {#each restaurantRatings as rating}
                        {#if rating.user?.id !== data.userContainer?.user?.id}
                            <li>{rating.rating} - {rating.comment}</li>
                        {:else}
                            <li>
                                <Input classLabel="hidden" label="Search" id={3} bind:value={rating.rating}
                                       placeHolder="Enter your rating"
                                       required={false}/>
                                <Input classLabel="hidden" label="Search" id={3} bind:value={rating.comment}
                                       placeHolder="Enter your comment"
                                       required={false}/>
                                <button class="bg-primary text-white p-2 rounded shadow" onclick={() => {
                                    updateRating(rating)
                                }}>Save
                                </button>
                                <button class="bg-primary text-white p-2 rounded shadow" onclick={() => {
                                    deleteRating(rating)
                                }}>delete
                                </button>
                            </li>
                        {/if}
                    {/each}
                </ul>
            </div>
        {/if}
        <!-- add ratings form -->
        <Input classLabel="hidden" label="Search" id={3} bind:value={newRatingNumber}
               placeHolder="Enter your rating"
               required={false}/>
        <Input classLabel="hidden" label="Search" id={3} bind:value={newRatingComment}
               placeHolder="Enter your comment"
               required={false}/>
        <button class="bg-primary text-white p-2 rounded shadow" onclick={()=>saveNewRating()}>Save
        </button>
    </div>
</div>

