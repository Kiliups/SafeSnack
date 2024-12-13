<script lang="ts">
    let {
        divClass = "",
        classLabel = "block text-sm font-medium",
        label = null,
        id = null,
        name = id,
        type = "text",
        value = $bindable(),
        inputClass = "mt-2 p-2 w-full rounded-md focus:outline-none focus:ring-2 focus:ring-accent",
        placeHolder,
        required = false,
    } = $props();
</script>

<div class={divClass}>
    {#if label}
        <label for={id} class={classLabel}>{label}</label>
    {/if}
    <input
            id={id}
            name={name}
            type={type}
            bind:value={value}
            class={inputClass}
            placeholder={placeHolder}
            required={required}
    />
</div>