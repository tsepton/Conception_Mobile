<script>
  export let value = "";
  let focus = false;
  // FIXME : let the cursor appear directly (input.select())
</script>

<style>
  input {
    width: 100%;
  }
  input {
    border: 0;
    width: 100%;
    font-weight: 100;
    border-bottom: 2px solid var(--back2);
    background: transparent;
  }
  input::placeholder {
    color: transparent;
  }
</style>

{#if focus}
  <input type="text" bind:value on:blur={() => (focus = false)} />
{:else}
  <div on:dblclick={() => (focus = true)}>
    <p>{value}</p>
  </div>
{/if}
