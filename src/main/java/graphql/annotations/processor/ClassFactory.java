package graphql.annotations.processor;

import graphql.annotations.processor.exceptions.GraphQLAnnotationsException;

import java.lang.reflect.Constructor;

public interface ClassFactory {
    <T> T constructNewInstance(Constructor<T> constructor, Object... args) throws GraphQLAnnotationsException;
    <T> T newInstance(Class<T> clazz, Object parameter);
    <T> T newInstance(Class<T> clazz) throws GraphQLAnnotationsException;
}
