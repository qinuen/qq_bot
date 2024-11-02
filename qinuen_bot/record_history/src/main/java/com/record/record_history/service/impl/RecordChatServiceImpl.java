package com.record.record_history.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper;
import com.record.record_history.entity.RecordChat;
import com.record.record_history.dao.RecordChatMapper;
import com.record.record_history.service.IRecordChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
@Service
public class RecordChatServiceImpl extends ServiceImpl<RecordChatMapper, RecordChat> implements IRecordChatService {
    @Autowired
    private RecordChatMapper recordChatMapper;
    @Override
    public boolean save(RecordChat entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<RecordChat> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<RecordChat> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeById(RecordChat entity) {
        return super.removeById(entity);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    @Override
    public boolean remove(Wrapper<RecordChat> queryWrapper) {
        return super.remove(queryWrapper);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        return super.removeByIds(list);
    }

    @Override
    public boolean removeByIds(Collection<?> list, boolean useFill) {
        return super.removeByIds(list, useFill);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list) {
        return super.removeBatchByIds(list);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, boolean useFill) {
        return super.removeBatchByIds(list, useFill);
    }

    @Override
    public boolean updateById(RecordChat entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean update(Wrapper<RecordChat> updateWrapper) {
        return super.update(updateWrapper);
    }

    @Override
    public boolean update(RecordChat entity, Wrapper<RecordChat> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<RecordChat> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public RecordChat getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public Optional<RecordChat> getOptById(Serializable id) {
        return super.getOptById(id);
    }

    @Override
    public List<RecordChat> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public List<RecordChat> listByMap(Map<String, Object> columnMap) {
        return super.listByMap(columnMap);
    }

    @Override
    public RecordChat getOne(Wrapper<RecordChat> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public Optional<RecordChat> getOneOpt(Wrapper<RecordChat> queryWrapper) {
        return super.getOneOpt(queryWrapper);
    }

    @Override
    public boolean exists(Wrapper<RecordChat> queryWrapper) {
        return super.exists(queryWrapper);
    }

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public long count(Wrapper<RecordChat> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<RecordChat> list(Wrapper<RecordChat> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public List<RecordChat> list(IPage<RecordChat> page, Wrapper<RecordChat> queryWrapper) {
        return super.list(page, queryWrapper);
    }

    @Override
    public List<RecordChat> list() {
        return super.list();
    }

    @Override
    public List<RecordChat> list(IPage<RecordChat> page) {
        return super.list(page);
    }

    @Override
    public <E extends IPage<RecordChat>> E page(E page, Wrapper<RecordChat> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public <E extends IPage<RecordChat>> E page(E page) {
        return super.page(page);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<RecordChat> queryWrapper) {
        return super.listMaps(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps(IPage<? extends Map<String, Object>> page, Wrapper<RecordChat> queryWrapper) {
        return super.listMaps(page, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return super.listMaps();
    }

    @Override
    public List<Map<String, Object>> listMaps(IPage<? extends Map<String, Object>> page) {
        return super.listMaps(page);
    }

    @Override
    public <E> List<E> listObjs() {
        return super.listObjs();
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return super.listObjs(mapper);
    }

    @Override
    public <E> List<E> listObjs(Wrapper<RecordChat> queryWrapper) {
        return super.listObjs(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<RecordChat> queryWrapper, Function<? super Object, V> mapper) {
        return super.listObjs(queryWrapper, mapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<RecordChat> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return super.pageMaps(page);
    }

    @Override
    public QueryChainWrapper<RecordChat> query() {
        return super.query();
    }

    @Override
    public LambdaQueryChainWrapper<RecordChat> lambdaQuery() {
        return super.lambdaQuery();
    }

    @Override
    public LambdaQueryChainWrapper<RecordChat> lambdaQuery(RecordChat entity) {
        return super.lambdaQuery(entity);
    }

    @Override
    public KtQueryChainWrapper<RecordChat> ktQuery() {
        return super.ktQuery();
    }

    @Override
    public KtUpdateChainWrapper<RecordChat> ktUpdate() {
        return super.ktUpdate();
    }

    @Override
    public UpdateChainWrapper<RecordChat> update() {
        return super.update();
    }

    @Override
    public LambdaUpdateChainWrapper<RecordChat> lambdaUpdate() {
        return super.lambdaUpdate();
    }

    @Override
    public boolean saveOrUpdate(RecordChat entity, Wrapper<RecordChat> updateWrapper) {
        return super.saveOrUpdate(entity, updateWrapper);
    }

    @Override
    public boolean insertByEntity(String group_number,String chat_data,String send_user) {
        return recordChatMapper.insertByEntity(group_number,chat_data,send_user);
    }
}
