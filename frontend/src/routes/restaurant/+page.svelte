<script lang="ts">
    import Input from "../../components/Input.svelte";
    import {type PageRestaurant, RestaurantControllerApi,} from "../../api_src";
    import {goto} from "$app/navigation";

    let searchInput = $state("");
    let searchResults = $state<PageRestaurant | null>(null);
    let currentPage = $state(1);
    let totalPages = $state(0);
    $effect(() => {
        if (searchInput.length % 2 === 0 && searchInput.length > 0) {
            console.log(searchInput);
            new RestaurantControllerApi().searchRestaurant({keyword: searchInput}, {credentials: 'include'})
                .then((response) => {
                        searchResults = response
                        totalPages = searchResults.totalPages!;
                        console.log("Search results:", searchResults);
                    }
                )
                .catch((error) => {
                        console.error("Error searching restaurant:", error);
                    }
                );
        }
    });

    function goToPage(page: number) {
        // todo kili: implement pagination
    }
</script>

<div class="min-h-screen bg-ground flex flex-col items-center p-12 mt-10">
    <Input divClass="w-full" classLabel="hidden" label="Search" id={3} bind:value={searchInput}
           placeHolder="Enter your search"
           required={true}/>

    {#if searchResults && searchResults.content}
        <div class="mt-8 w-full max-w-2xl">
            <!-- Results List -->
            <div class="grid grid-cols-1 gap-4">
                {#each searchResults.content as restaurant}
                    <button class="bg-white text-ground p-4 rounded shadow" onclick={()=>{
                        goto(`/restaurant/${restaurant.id}`);
                    }}>
                        {restaurant.name}
                    </button>
                {/each}
            </div>

            <!-- Pagination Controls -->
            <div class="mt-6 flex justify-center items-center space-x-4">
                <button
                        onclick={() => goToPage(currentPage - 1)}
                        class="px-4 py-2 bg-primary text-white rounded shadow hover:bg-secondary disabled:opacity-50"
                        disabled={currentPage === 1}
                >
                    Previous
                </button>
                <span class="text-sm text-white">Page {currentPage} of {totalPages}</span>
                <button
                        onclick={() => goToPage(currentPage + 1)}
                        class="px-4 py-2 bg-primary text-white rounded shadow hover:bg-secondary disabled:opacity-50"
                        disabled={currentPage === totalPages}
                >
                    Next
                </button>
            </div>
        </div>
    {/if}
</div>