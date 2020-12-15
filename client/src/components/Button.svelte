<script>
  import { createEventDispatcher } from "svelte";
  import { link } from "svelte-routing";

  export let href;
  const dispatch = createEventDispatcher();
</script>

<style>
  a {
    display: inline-block;
    padding: 0.35em 1.2em;
    border: 0.1em solid #ffffff;
    margin: 0 0.3em 0.3em 0;
    border-radius: 0.12em;
    box-sizing: border-box;
    text-decoration: none;
    font-weight: 100;
    color: #ffffff;
    text-align: center;
    transition: all 0.2s;
  }
  a:hover {
    color: #000000;
    border: 0.1em solid var(--highlight1);
    background-image: linear-gradient(
      to right,
      var(--highlight1),
      var(--highlight2)
    );
    cursor: pointer;
  }
</style>

<a use:link on:click={() => dispatch('click')} {href}><slot /></a>
