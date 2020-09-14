import request from '@/utils/request'

// 查询建议零售价列表
export function listCalendar(query) {
  
  if(query.sku !== undefined){

 query.skuEncode = encodeURIComponent(query.sku)
 query.sku = undefined
  }
  return request({
    url: '/tek/calendar/queryCalendarAll',
    method: 'get',
    params: query
  })
  
}

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}


// 修改msrpOther
export function updateMsrpOtherMethod(data) {
  return request({
    url: '/tek/newTekMsrp/editMsrpOther',
    method: 'get',
    params: data
  })
}



// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
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

// 删除角色
export function getCountryList() {
  return request({
    url: '/tek/newTekMsrpWk/queryMsrpWKCountryList',
    method: 'get'
  })
}

// 导出角色
export function exportRole(query) {
  return request({
    url: '/system/role/export',
    method: 'get',
    params: query
  })
}