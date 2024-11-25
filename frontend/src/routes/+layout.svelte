<script lang="ts">
    import '../app.css';
    import Navbar from "../components/Navbar.svelte";

    let {children} = $props();
</script>

<Navbar/>

<section class="w-screen h-screen">
    {@render children()}
</section>

