<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'

// 对应后端 Result<T>，data 承载具体接口的业务数据。
interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

// 对应后端 PageResult<T>；records 是当前页记录，其余字段供分页器使用。
interface PageResult<T> {
  total: number
  pages: number
  current: number
  size: number
  records: T[]
}

// 商品列表只使用 ProductVO 对外提供的字段。
interface Product {
  id: number
  categoryId: number
  name: string
  price: number
  stock: number
  description: string
  imageUrl: string | null
}

const products = ref<Product[]>([])

const loadProducts = async () => {
  const response = await axios.get<ApiResponse<PageResult<Product>>>('/api/products')
  // 默认加载第一页，从统一响应的分页对象中提取商品数组。
  products.value = response.data.data.records
}

onMounted(() => {
  loadProducts()
})
</script>

<template>
  <main class="container">
    <h1>商城商品列表</h1>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>商品名称</th>
        <th>价格</th>
        <th>库存</th>
        <th>描述</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="product in products" :key="product.id">
        <td>{{ product.id }}</td>
        <td>{{ product.name }}</td>
        <td>¥{{ product.price }}</td>
        <td>{{ product.stock }}</td>
        <td>{{ product.description }}</td>
      </tr>
      </tbody>
    </table>
  </main>
</template>

<style scoped>
.container {
  width: 1000px;
  margin: 60px auto;
}

h1 {
  margin-bottom: 30px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 14px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background: #f5f5f5;
}
</style>
