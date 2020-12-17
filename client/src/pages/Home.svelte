<script>
  import { navigate } from "svelte-routing";
  import { fade, fly } from "svelte/transition";
  import Button from "../components/Button.svelte";
  import Input from "../components/Input.svelte";
  import rooms from "../stores/rooms.js";

  let showInput = false;
</script>

<style>
  main {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  h1,
  h6 {
    color: white;
    font-weight: 100;
  }
  h6 {
    padding-bottom: 1rem;
  }
  div[center] {
    text-align: center;
  }
</style>

<main in:fly={{ y: 200, duration: 1200 }}>
  <div center>
    <h1>Welcome to Mõla</h1>
    <h6>What do you want to do ?</h6>
    {#if showInput}
      <div in:fade={{ duration: 800 }}>
        <Input
          on:enter={(e) => navigate(e.detail.value)}
          on:blur={() => (showInput = false)}>
          Enter the room ID
        </Input>
      </div>
    {:else}
      <div in:fade={{ duration: 800 }}>
        {#if $rooms.length}
          <Button on:click={() => (showInput = true)}>Join room</Button>
        {/if}
        <Button on:click={rooms.create}>Create room</Button>
      </div>
    {/if}
  </div>
</main>
