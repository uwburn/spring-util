package it.mgt.util.spring.repository;

public abstract class BaseActiveRecord<T, K> {

    protected abstract BaseRepository<T, K> getRepository();

    protected abstract T getEntity();

    public void remove() {
        getRepository().remove(getEntity());
    }

    public void refresh() {
        getRepository().refresh(getEntity());
    }

    public void persist() {
        getRepository().persist(getEntity());
    }

    public T merge() {
        return getRepository().merge(getEntity());
    }

    public T saveOrUpdate() {
        return getRepository().saveOrUpdate(getEntity());
    }

}
