import request from '@/utils/request'

// 查询零售价列表
export function listMsrp(query) {
  return request({
    url: '/tek/newTekMsrp/queryNewTekMsrpByPage',
    method: 'get',
    params: query
  })
}


// 查询周零售价列表
export function listMsrpWK(query) {
  return request({
    url: '/tek/newTekMsrpWk/queryNewTekMsrpWKByPage',
    method: 'get',
    params: query
  })
}


// 查询所有的SKU
export function listProduct() {
  return request({
    url: '/tek/skuToMatnr/getAllSku',
    method: 'get'
  })
}


// 查询所有已维护的SKU
export function listProductModel(query) {
  return request({
    url: '/tek/newTekProductModel/getAllProductModel',
    method: 'get',
    params: query
  })
}

// 查询所有的产品系列
export function listModel() {
  return request({
    url: '/tek/newTekModel/getAllModel',
    method: 'get'
  })
}


// 查询所有的客户信息
// export function listCustomer() {
//   return request({
//     url: '/tek/newTekCustomer/getAllCustomer ',
//     method: 'get'
//   })
// }


// 新增产品关联系列
export function addProductModel(data) {
  return request({
    url: '/tek/newTekProductModel/add',
    method: 'post',
    data: data
  })
}

// 新增产品零售价
export function addMsrp(data) {
  return request({
    url: '/tek/newTekMsrp/add',
    method: 'post',
    data: data
  })
}


// 新增周产品零售价
export function addMsrpWk(data) {
  return request({
    url: '/tek/newTekMsrpWk/add',
    method: 'post',
    data: data
  })
}

// 查询产品零售价详细
export function getMsrp(id) {
  return request({
    url: '/tek/newTekMsrp/' + id,
    method: 'get'
  })
}


// 查询周零售价格详情
export function getMsrpWK(id) {
  return request({
    url: '/tek/newTekMsrpWk/' + id,
    method: 'get'
  })
}

// 修改产品系列
export function updateMsrp(data) {
  return request({
    url: '/tek/newTekMsrp/',
    method: 'put',
    data: data
  })
}


// 修改周建议价格
export function updateMsrpWK(data) {
  return request({
    url: '/tek/newTekMsrpWk/',
    method: 'put',
    data: data
  })
}

// 删除产品系列
export function delModel(id) {
  return request({
    url: '/tek/newTekModel/' + id,
    method: 'delete'
  })
}


export function delMsrp(id) {
  return request({
    url: '/tek/newTekMsrp/' + id,
    method: 'delete'
  })
}

export function delMsrpWK(id) {
  return request({
    url: '/tek/newTekMsrpWk/' + id,
    method: 'delete'
  })
}

// 导出MSRP
export function exportMSRP(query) {
  return request({
    url: '/tek/newTekMsrp/export',
    method: 'get',
    params: query
  })
}

// 导出SellingPrice
export function exportSellingPrice(query) {
  return request({
    url: '/tek/newTekMsrpWk/export',
    method: 'get',
    params: query
  })
}

// 查询所有的SKU
export function listGoods() {
  return request({
    url: '/tek/skuToMatnr/getAllSku',
    method: 'get'
  })
}

// 查询所有国家
export function listCountry() {
  return request({
    url: '/tek/newTekCurrencyCode/queryNewTekCurrencyCodeAll',
    method: 'get'
  })
}

// 查询所有的客户（根据国家查询）
export function listCustomer(country) {
  // let id = data;
  return request({
    url: '/tek/newTekCustomer/getAllCustomer/' + country,
    method: 'get'
  })
}


export function listCustomerForPermission(country) {
  // let id = data;
  return request({
    url: '/tek/newTekCustomer/getCustomerInfoForPermission',
    method: 'get'
  })
}



// 查询sku所对应的country
export function querySKU(sku) {
  return request({
    url: '/tek/newTekProductModel/getProductModelBySKU/',
    method: 'get',
    params: {
      sku: sku
    }
  })
}

// 查询所有币种
export function listCurrencyCode(country) {
  return request({
    url: '/tek/newTekCurrencyCode/queryNewTekCurrencyCodeAll',
    method: 'get',
    params: {
      country: country
    }
  })
}


// 查询所有指定国家的币种
export function getCurrencyByCountry(country) {
  return request({
    url: '/tek/newTekCurrencyCode/getCurrencyCodeByCountry',
    method: 'get',
    params: {
      country: country
    }
  })
}


// 根据SKU查询对应的country
export function getCountryBySKU(sku) {
  return request({
    url: '/tek/newTekProductModel/getProductModelBySKU',
    method: 'get',
    params: {
      sku: sku
    }
  })
}

// 查询所有国家
export function listCountryCode(query) {
  return request({
    url: '/tek/newTekCountryCode/queryNewTekCountryCodeAll',
    method: 'get',
    params: {
      code : query
    }
  })
}

// 查询所有国家
export function listCountryCodeForPermission() {
  return request({
    url: '/tek/newTekCountryCode/queryCountryCodeForPermission',
    method: 'get'
  })
}


export function listPromotionType() {
  return request({
    url: '/tek/newTekPromotionType/queryNewTekPromotionTypeAll',
    method: 'get'
  })
}

// 下载Selling Price导入模板
export function importTemplate() {
  return request({
    url: '/tek/newTekMsrpWk/importTemplate',
    method: 'get'
  })
}

// 下载用户导入模板
export function importMSRPTemplate() {
  return request({
    url: '/tek/newTekMsrp/importTemplate',
    method: 'get'
  })
}

// 查询所有的产品品类
export function listCategory() {
  return request({
    url: '/tek/newTekCategory/getAllCategory',
    method: 'get'
  })
}

// 查询所有的产品系列
export function listModelForCategoryId(categoryId) {
  // let id = data;
  return request({
    url: '/tek/newTekModel/getAllModel/' + categoryId,
    method: 'get'
  })
}

// 查询所有的产品配置
export function listDispose(modelId) {
  // let id = data;
  return request({
    url: '/tek/newTekDispose/getAllDispose/' + modelId,
    method: 'get'
  })
}