<script lang="ts">
    import type {PageData} from './$types';

    let {data}: { data: PageData } = $props();
    let restaurant = data.restaurant;
    console.log(data);
</script>

<div class="h-full bg-gray-100 text-gray-900 mt-10">
    <div class="container mx-auto py-10">
        <h1 class="text-4xl font-bold text-center mb-8">{restaurant.name}</h1>

        <div class="bg-white rounded-lg shadow-lg p-6">
            <h2 class="text-2xl font-semibold mb-4">Address</h2>
            <p class="text-gray-700">{restaurant.address?.street},</p>
            <p class="text-gray-700">{restaurant.address?.city},</p>
            <p class="text-gray-700">{restaurant.address?.zip}</p>
        </div>

        <div class="bg-white rounded-lg shadow-lg p-6 mt-6">
            <h2 class="text-2xl font-semibold mb-4">Description</h2>
            <p class="text-gray-700">{restaurant.description}</p>
        </div>

        <div class="bg-white rounded-lg shadow-lg p-6 mt-6">
            <h2 class="text-2xl font-semibold mb-4">Menu</h2>
            <ul class="list-disc list-inside text-gray-700">
                {#if restaurant.menu}
                    {#each restaurant?.menu as item}
                        <li>{item}</li>
                    {/each}
                {/if}
            </ul>
        </div>
    </div>
</div>

