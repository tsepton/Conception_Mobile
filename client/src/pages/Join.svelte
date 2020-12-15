<script>
  import { fly } from "svelte/transition";
  import Input from "../components/Input.svelte";
</script>

<style>
  main {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  [center] {
    text-align: center;
    border: 1px solid red;
    min-width: max-content;
  }
</style>

<main in:fly={{ y: 200, duration: 1200 }}>
  <div center>
    <Input>Enter the room ID</Input>
  </div>
</main>
