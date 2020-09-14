import request from '@/utils/request'

// 查询产品配置分页列表
export function listDispose(query) {
  return request({
    url: '/tek/newTekDispose/queryNewTekDisposeAll',
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


// 查询所有的产品系列
export function listModel(categoryId) {
  // let id = data;
  return request({
    url: '/tek/newTekModel/getAllModel/' + categoryId,
    method: 'get'
  })
}


// 新增产品配置
export function addDispose(data) {
  return request({
    url: '/tek/newTekDispose/add',
    method: 'post',
    data: data
  })
}

// 查询产品配置详细
export function getDispose(id) {
  return request({
    url: '/tek/newTekDispose/' + id,
    method: 'get'
  })
}

// 修改产品配置
export function updateDispose(data) {
  return request({
    url: '/tek/newTekDispose/',
    method: 'put',
    data: data
  })
}

// 删除产品配置
export function delDispose(ids) {
  return request({
    url: '/tek/newTekDispose/' + ids,
    method: 'delete'
  })
}

// 导出配置
export function exportDispose(query) {
  return request({
    url: '/tek/newTekDispose/export',
    method: 'get',
    params: query
  })
}