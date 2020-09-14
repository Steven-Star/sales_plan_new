import request from '@/utils/request'

// 查询产品系列列表
export function listSalesPlan(query) {
  return request({
    url: '/tek/salesPlan/getSalesPlanInfo',
    method: 'post',
    params: query
  })
}

// 新增产品系列
export function addModel(data) {
  return request({
    url: '/tek/newTekModel/add',
    method: 'post',
    data: data
  })
}

// 查询产品系列详细
export function getModel(id) {
  return request({
    url: '/tek/newTekModel/' + id,
    method: 'get'
  })
}

// 修改产品系列
export function updateModel(data) {
  return request({
    url: '/tek/newTekModel/',
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

// 导出角色
export function exportRole(query) {
  return request({
    url: '/system/role/export',
    method: 'get',
    params: query
  })
}