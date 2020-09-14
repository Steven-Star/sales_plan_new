import request from '@/utils/request'

// 查询产品配置组分页列表
export function listDisposeGroup(query) {
  return request({
    url: '/tek/newTekDisposeGroup/queryNewTekDisposeGroupForPage',
    method: 'get',
    params: query
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


// 新增产品配置组
export function addDisposeGroup(data) {
  return request({
    url: '/tek/newTekDisposeGroup/add',
    method: 'post',
    data: data
  })
}

// 查询产品配置组详细
export function getDisposeGroup(id) {
  return request({
    url: '/tek/newTekDisposeGroup/' + id,
    method: 'get'
  })
}

// 修改产品配置组
export function updateDisposeGroup(data) {
  return request({
    url: '/tek/newTekDisposeGroup/',
    method: 'put',
    data: data
  })
}

// 删除产品配置
export function delDisposeGroup(ids) {
  return request({
    url: '/tek/newTekDisposeGroup/' + ids,
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