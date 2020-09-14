import request from '@/utils/request'

// 查询产品系列列表
export function listModel(query) {
  return request({
    url: '/tek/newTekModel/queryNewTekModelAll',
    method: 'get',
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

// 查询所有的产品品类
export function listCategory() {
  return request({
    url: '/tek/newTekCategory/getAllCategory',
    method: 'get'
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

// 导出系列
export function exportModel(query) {
  return request({
    url: '/tek/newTekModel/export',
    method: 'get',
    params: query
  })
}