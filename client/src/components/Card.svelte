<script>
  import { createEventDispatcher } from "svelte";

  import { fade } from "svelte/transition";

  const dispatch = createEventDispatcher();

  function remove() {
    dispatch("delete");
  }
</script>

<style>
  [card] {
    position: relative;
    font-weight: 100;
    color: #000;
    background-color: #e0e0e0;
    padding: 10px;
    border-radius: 0.12em;
  }
  [footer] {
    padding-top: 10px;
  }
  h1 {
    font-size: larger;
    font-weight: 400;
  }
  .close-button {
    position: absolute;
    top: 0;
    right: 5px;
    cursor: pointer;
    color: #8f8f8f;
  }
</style>

<div card in:fade>
  <div header>
    <h1>
      <slot name="title" />
    </h1>
    <div class="close-button" on:click={remove}><span>&times;</span></div>
  </div>

  <div body>
    <slot name="body" />
  </div>

  <div footer>
    <slot name="footer" />
  </div>
</div>
