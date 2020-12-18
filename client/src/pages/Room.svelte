<script>
  import room from "../stores/room.js";
  import { onDestroy, onMount } from "svelte";
  import { fade } from "svelte/transition";
  import Button from "../components/Button.svelte";
  import Card from "../components/Card.svelte";
  import TextEdit from "../components/TextEdit.svelte";
  import { navigate } from "svelte-routing";

  // Tell the server we have updated one of our card 
  // each time a binded value is udpated
  $:$room && $room.data, room.cards.update();

  onMount(() => {
    // If room does not exist, go back to home page
    if (!$room && $room.id) {
      console.warn("Room does not exist.");
      room.leave();
      navigate("/", { replace: true });
    }
  });

  onDestroy(() => {
    // Tell the server we're leaving the room
    if ($room && $room.id) {
      console.log("Leaving room.");
      room.leave();
    }
  });
</script>

<style>
  main {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  .container {
    width: auto;
    flex-grow: 4;

    /* Relative to its children */
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
    align-items: center;
    padding: 25px;
    overflow-y: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  }
  .container::-webkit-scrollbar {
    display: none;
  }
  .title {
    color: #fff;
    padding: 15px;
    margin: 0;
    font-weight: 100;
    box-shadow: 0 0 1rem 0.1rem rgba(0, 0, 0, 0.342);
    cursor: pointer;
  }
  .card-item {
    margin: 10px;
    max-width: 33vw;
    min-width: min-content;
    flex-grow: 4;
  }
  .button-container {
    padding: 15px;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    box-shadow: 0 0 1rem 0.1rem rgba(0, 0, 0, 0.342);
  }
</style>

<main in:fade={{ duration: 1200 }}>
  <h1 on:click={() => navigate('/', { replace: true })} class="title">
    Mõla n°{$room.id}
  </h1>
  <div class="container" in:fade>
    {#each $room.cards as card}
      <div class="card-item">
        <Card on:delete={() => room.cards.delete(card.id)}>
          <div slot="title">
            <TextEdit bind:value={card.title} />
          </div>
          <div slot="body">
            <TextEdit bind:value={card.body} />
          </div>
        </Card>
      </div>
    {/each}
  </div>
  <div class="button-container" in:fade>
    <Button on:click={room.cards.add}>Add card</Button>
  </div>
</main>
