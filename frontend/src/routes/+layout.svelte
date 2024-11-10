<script lang="ts">
    import '../app.css';

    let {children} = $props();
</script>

<section class="w-screen h-screen">
    {@render children()}
</section>

