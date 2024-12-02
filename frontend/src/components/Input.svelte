<script>
    let {
        divClass = "",
        classLabel = "block text-sm font-medium",
        label,
        id,
        name = id,
        type = "text",
        value = $bindable(),
        inputClass = "mt-2 p-2 w-full rounded-md focus:outline-none focus:ring-2 focus:ring-accent",
        placeHolder,
        required = false
    } = $props();
</script>

<div class={divClass}>
    <label for={id} class={classLabel}>{label}</label>
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