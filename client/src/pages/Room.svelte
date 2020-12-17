<script>
  import { navigate } from "svelte-routing";
  import { fade } from "svelte/transition";
  import Button from "../components/Button.svelte";
  import Card from "../components/Card.svelte";
  import TextEdit from "../components/TextEdit.svelte";
  import store from "../stores/websockets.js";

  let id;
  let cards = [
    {
      id: 1,
      title: "Title",
      body: "Enter your text here",
    },
  ];

  // TODO :  Link with backend
  function addCard() {
    const lastCard = cards.pop();
    if (lastCard) {
      cards = [
        ...cards,
        lastCard,
        { id: lastCard.id + 1, title: "Title", body: "Enter your text here" },
      ];
    } else {
      cards = [{ id: 1, title: "Title", body: "Enter your text here" }];
    }
  }

  function removeCard(id) {
    cards = cards.filter((card) => card.id !== id);
  }

  $: id = parseInt(location.href.split("/").pop());
  $: id, !$store.includes(id) && navigate("/");
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
  <h1 on:click={() => navigate('/')} class="title">Mõla n°{id}</h1>
  <div class="container" in:fade>
    {#each cards as card}
      <div class="card-item">
        <Card on:delete={() => removeCard(card.id)}>
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
    <Button on:click={addCard}>Add card</Button>
  </div>
</main>
