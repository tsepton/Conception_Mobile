<script>
  import { fly, fade } from "svelte/transition";
  import Button from "../components/Button.svelte";
  import Card from "../components/Card.svelte";

  let id;
  let cards = [{}, {}, {}, {}, {}, {}, {}, {}, {}];
  // TODO :  Link with backend

  function addCard() {
    cards = [...cards, {}];
  }

  $: id = window.location.href.split("/").pop();
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
    justify-content: space-between;
    align-items: center;
    padding-right: 25px;
    padding-left: 25px;
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
  }
  .card-item {
    margin: 10px;
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
  <h1 class="title">Mõla n°{id}</h1>
  <div class="container" in:fade>
    {#each cards as _}
      <div class="card-item">
        <Card>
          <div slot="title">title</div>
          <div slot="body">Lorem ipsum dolores est ma couille</div>
          <div slot="footer">footer</div>
        </Card>
      </div>
    {/each}
  </div>
  <div class="button-container" in:fade>
    <Button on:click={addCard}>Add card</Button>
  </div>
</main>
