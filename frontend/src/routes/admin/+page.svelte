<script lang="ts">
    import {isAdmin} from '../../stores/user.js';
</script>

<h1 class="flex flex-col justify-center items-center w-full h-full">
    {#if $isAdmin}
        <p>Admin Dashboard</p>
        <p>Welcome back!</p>
    {:else}
        <p>Sorry</p>
        <p>You don't have access to the admin page.</p>
    {/if}
</h1>