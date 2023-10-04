<template>
  <div id="back">
    <div class="container">
      <h2>Add Product</h2>
    </div>
    <div class="center-form">
      <form @submit.prevent="save">
      <div class="form-group">
        <label>Product name</label>
        <input type="text" v-model="product.name" class="form-control custom-code" placeholder="Product name">
      </div>
      <div class="form-group">
        <label>Product Description</label>
        <input type="text" v-model="product.description" class="form-control custom-code" placeholder="Product Description">
      </div>
      <div class="form-group">
        <label>Product price</label>
        <input type="text" v-model="product.price" class="form-control custom-code" placeholder="Product Price">
      </div>
      <button type="submit" id="save-button" class="btn btn-primary">Save</button>
    </form>

    </div>
    <div class="container mt-4">
      <h2>View Products</h2>
    </div>
    <table class="table ">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Name</th>
          <th scope="col">Description</th>
          <th scope="col">Price</th>
          <th scope="col">Option</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in result" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.price }} AED</td>
          <td>
            <button type="button" class="btn btn-warning" @click="edit(product)">Edit</button>
            <button type="button" class="btn btn-danger" @click="remove(product)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProductCrud',
  data () {
    return {
      result: [],
      product: {
        id: '',
        name: '',
        description: '',
        price: ''
      }
    }
  },
  created () {
    this.loadProducts()
  },
  methods: {
    loadProducts () {
      axios.get('http://localhost:9090/products')
        .then(({ data }) => {
          this.result = data
        })
    },
    save () {
      if (this.product.id === '') {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    saveData () {
      axios.post('http://localhost:9090/product', this.product)
        .then(() => {
          alert('Saved')
          this.clearForm()
          this.loadProducts()
        })
        .catch((error) => {
          alert('Error ' + error.response.status + ': ' + error.response.data)
        })
    },
    edit (product) {
      this.product = { ...product } // Copy the product to avoid reactivity issues
    },
    updateData () {
      axios.put(`http://localhost:9090/product/${this.product.id}`, this.product)
        .then(() => {
          alert('Updated')
          this.clearForm()
          this.loadProducts()
        })
        .catch((error) => {
          alert('Error ' + error.response.status + ': ' + error.response.data)
        })
    },
    remove (product) {
      axios.delete(`http://localhost:9090/product/${product.id}`)
        .then(() => {
          alert('Deleted')
          this.loadProducts()
        })
        .catch((error) => {
          alert('Error ' + error.response.status + ': ' + error.response.data)
        })
    },
    clearForm () {
      this.product.id = ''
      this.product.name = ''
      this.product.description = ''
      this.product.price = ''
    }
  }
}
</script>

<style>
#back {
    font-family: 'Arial', sans-serif;
    color: #333;
    background-color: #f0f0f0;
    padding: 10px 20px;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
h2{
  font-family: 'Arial', sans-serif;
  color: #fff;
  background-color: #142f4b;
  padding: 5px 5px;
  border-radius: 20px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
h2:hover {
  background-color: #0056b3;
}
.container{
  align-content: center;
}
.custom-code{
  width: 200px;
  border-radius: 10px;
  text-align: center;
  padding: 5px;
}
.custom-input:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}
.center-form{
  display: flex;
  justify-content: center;
  align-items: center;
}
#save-button {
  margin-top: 5px;
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
#save-button:hover {
  background-color: #0056b3;
}

</style>
