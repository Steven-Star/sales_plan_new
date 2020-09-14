import request from '@/utils/request'

// 查询客户列表
export function listCustomer(query) {
  return request({
    url: '/tek/newTekCustomer/queryNewTekCustomerAll',
    method: 'get',
    params: query
  })
}

// 查询客户详细
export function getCustomer(id) {
  return request({
    url: '/tek/newTekCustomer/' + id,
    method: 'get'
  })
}

// 新增客户信息
export function addCustomer(data) {
  return request({
    url: '/tek/newTekCustomer/add',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateCustomer(data) {
  return request({
    url: '/tek/newTekCustomer/',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delCustomer(id) {
  return request({
    url: '/tek/newTekCustomer/' + id,
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

// 导出客户
export function exportCustomer(query) {
  return request({
    url: '/tek/newTekCustomer/export',
    method: 'get',
    params: query
  })
}


// 查询所有国家
// export function listCountryCode() {
//   return request({
//     url: '/tek/newTekCountryCode/queryNewTekCountryCodeAll',
//     method: 'get'
//   })
// }

// 查询所有国家
export function listCountryCode() {
  return request({
    url: '/tek/newTekCountryCode/queryCountryCodeForPermission',
    method: 'get'
  })
}
