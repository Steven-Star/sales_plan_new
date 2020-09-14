import request from '@/utils/request'
import { valid } from 'mockjs'

// 查询产品系列列表
// export function listSpecialEvent(data) {
//   return request({
//     url: '/tek/newTekMsrpWk/queryPromotionListByPage',
//     method: 'get',
//     params: {
//       year: data.year,
//       promotionType: data.promotionType
//     }
//   })
// }

export function listSpecialEvent(data) {
  return request({
    url: '/tek/newTekMsrpWk/queryPromotionListByPage',
    method: 'get',
    params: data
  })
}

// 查询promotion type 列表
export function listPromotionType(query) {
  return request({
    url: '/tek/newTekPromotionType/queryNewTekPromotionTypeAll',
    method: 'get',
    params: query
  })
}


// 根据所选日期和promotionType 查询promotion type 列表
export function listPromotionTypeByDate(validFrom,promotionType,country) {
  return request({
    url: '/tek/newTekMsrpWk/queryByDateAndPromotionType',
    method: 'get',
    params: {
      validFromWK: validFrom,
      promotionType: promotionType,
      country: country
    }
  })
}

// 查询PromotionCanendar列表,不带分页
export function getAllSpecialEvent(country) {
  return request({
    url: '/tek/newTekMsrpWk/queryPromotionCalendarList',
    method: 'get',
    params: {
      country: country
    }
  })
}

// 新增产品系列
export function addSpecialEvent(data) {
  return request({
    url: '/tek/specialEvent/add',
    method: 'post',
    data: data
  })
}

export function addPromotionType(data) {
  return request({
    url: '/tek/newTekPromotionType/add',
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


export function getSpecialEvent(id) {
  return request({
    url: '/tek/specialEvent/' + id,
    method: 'get'
  })
}

export function getPromotionType(id) {
  return request({
    url: '/tek/newTekPromotionType/' + id,
    method: 'get'
  })
}


export function updateSpecialEvent(data) {
  return request({
    url: '/tek/specialEvent/',
    method: 'put',
    data: data
  })
}

export function updatePromotionType(data) {
  return request({
    url: '/tek/newTekPromotionType/',
    method: 'put',
    data: data
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

export function delPromotionType(ids) {
  return request({
    url: '/tek/newTekPromotionType/' + ids,
    method: 'delete'
  })
}


// 查询所有国家
export function listCountryCode() {
  return request({
    url: '/tek/newTekCountryCode/queryNewTekCountryCodeAll',
    method: 'get'
  })
}

export function listCountryCodeForPermission() {
  return request({
    url: '/tek/newTekCountryCode/queryCountryCodeForPermission',
    method: 'get'
  })
}

// 导出PromotionList
export function exportPromotionList(query) {
  return request({
    url: '/tek/newTekMsrpWk/exportPromotionList',
    method: 'get',
    params: query
  })
}


// 导出PromotionType
export function exportPromotionType(query) {
  return request({
    url: '/tek/newTekPromotionType/export',
    method: 'get',
    params: query
  })
}
