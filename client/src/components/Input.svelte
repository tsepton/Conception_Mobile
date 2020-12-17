<!-- Adaptetd from : https://codepen.io/lucasyem/pen/ZEEYKdj?editors=1100 -->
<script>
  import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();
  let value = undefined;

  function handleKeyup(e) {
    e.code === "Enter" && dispatch("enter", { value });
  }
</script>

<style>
  .group {
    position: relative;
    padding: 15px 0 0;
    width: 100%;
    min-width: max-content;
  }

  .field {
    border: 0;
    width: 100%;
    color: #fff;
    border-bottom: 2px solid #fff;
    background: transparent;
  }
  .field::placeholder {
    color: transparent;
  }
  .field:placeholder-shown ~ .label {
    font-size: 0.8rem;
    cursor: text;
    top: 22px;
  }

  .label {
    position: absolute;
    top: 0;
    display: block;
    transition: 0.2s;
    font-size: 0.8rem;
    color: #ebebeb;
  }

  .field:focus {
    border-image: linear-gradient(
      to right,
      var(--highlight1),
      var(--highlight2)
    );
    border-image-slice: 1;
  }
  .field:focus ~ .label {
    position: absolute;
    top: 0;
    display: block;
    transition: 0.2s;
    font-size: 0.8rem;
    color: #11998e;
  }

  /* reset input */
  .field:required,
  .field:invalid {
    box-shadow: none;
  }
</style>

<div class="group">
  <input
    type="input"
    class="field"
    placeholder=""
    maxlength="4"
    bind:value
    on:blur={() => dispatch('blur')}
    on:keyup|preventDefault={handleKeyup} />
  <label for="name" class="label"><slot /></label>
</div>
