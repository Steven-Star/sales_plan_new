import request from '@/utils/request'

// 查询产品关联系列列表
export function listProductModel(query) {
  return request({
    url: '/tek/newTekProductModel/getNewTekProductModel',
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

// 查询所有的产品品类
export function listCategory() {
  return request({
    url: '/tek/newTekCategory/getAllCategory',
    method: 'get'
  })
}


// 查询所有的产品系列
export function listModel(categoryId) {
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

// 查询所有国家(查询币别表)
export function listCountry() {
  return request({
    url: '/tek/newTekCurrencyCode/queryNewTekCurrencyCodeAll',
    method: 'get'
  })
}


// 查询所有国家(查询国家和地区代码表)
export function listCountryCode() {
  return request({
    url: '/tek/newTekCountryCode/queryNewTekCountryCodeAll',
    method: 'get'
  })
}


// 新增产品关联
export function addProductModel(data) {
  return request({
    url: '/tek/newTekProductModel/add',
    method: 'post',
    data: data
  })
}

// 查询产品系列详细
export function getProductModel(id) {
  return request({
    url: '/tek/newTekProductModel/' + id,
    method: 'get'
  })
}

// 修改产品关联
export function updateProductModel(data) {
  return request({
    url: '/tek/newTekProductModel/',
    method: 'put',
    data: data
  })
}

// 删除产品系列
export function delProductModel(id) {
  return request({
    url: '/tek/newTekProductModel/' + id,
    method: 'delete'
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return request({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  })
}

// 导出SKU
export function exportSKU(query) {
  return request({
    url: '/tek/newTekProductModel/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/tek/newTekProductModel/importTemplate',
    method: 'get'
  })
}