<script lang="ts">
    import '../app.css';
    import Navbar from "../components/Navbar.svelte";
    import {authStore} from "../stores/auth";

    let {children, data} = $props();

    $effect(() => {
        if (data.userContainer) {
            $authStore = data.userContainer;
        }
    });
</script>

<Navbar/>
<section class="w-screen h-screen">
    {@render children()}
</section>

