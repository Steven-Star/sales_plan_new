import request from '@/utils/request'

// 查询产品品类列表
export function listCategory(query) {
  return request({
    url: '/tek/newTekCategory/queryNewTekCategoryAll',
    method: 'get',
    params: query
  })
}

// 新增产品品类
export function addCategory(data) {
  return request({
    url: '/tek/newTekCategory/add',
    method: 'post',
    data: data
  })
}

// 查询产品品类详细
export function getCategory(id) {
  return request({
    url: '/tek/newTekCategory/' + id,
    method: 'get'
  })
}

// 修改产品系列
export function updateCategory(data) {
  return request({
    url: '/tek/newTekCategory/',
    method: 'put',
    data: data
  })
}

// 删除产品品类
export function delCategory(ids) {
  return request({
    url: '/tek/newTekCategory/' + ids,
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
export function exportCategory(query) {
  return request({
    url: '/tek/newTekCategory/export',
    method: 'get',
    params: query
  })
}

// 查询所有的产品配置组
export function listDisposeGroup() {
  return request({
    url: '/tek/newTekDisposeGroup/getAllDisposeGroup',
    method: 'get'
  })
}