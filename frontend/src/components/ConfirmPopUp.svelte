<script lang="ts">
    export let title = "Confirm Action"; // Default title
    export let message = "Are you sure you want to proceed?"; // Default message
    export let confirmLabel = "Yes"; // Label for confirm button
    export let cancelLabel = "Cancel"; // Label for cancel button

    export let onConfirm: Function; // Callback for confirm action
    export let onCancel: Function; // Callback for cancel action

    function confirm() {
        if (onConfirm) onConfirm();
    }

    function cancel() {
        if (onCancel) onCancel();
    }
</script>

<div class="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50 z-50">
    <div class="bg-white rounded-lg shadow-lg p-6 max-w-sm w-full space-y-4">
        <h2 class="text-xl font-semibold text-gray-800">{title}</h2>
        <p class="text-gray-600">{message}</p>
        <div class="flex space-x-4">
            <button
                    on:click={confirm}
                    class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition"
            >
                {confirmLabel}
            </button>
            <button
                    on:click={cancel}
                    class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 transition"
            >
                {cancelLabel}
            </button>
        </div>
    </div>
</div>