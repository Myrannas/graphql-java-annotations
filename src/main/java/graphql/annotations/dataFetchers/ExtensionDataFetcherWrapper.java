/**
 * Copyright 2016 Yurii Rashkovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */
package graphql.annotations.dataFetchers;

import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;

import java.util.Map;

public class ExtensionDataFetcherWrapper<T> implements DataFetcher<T>{

    private final Class declaringClass;

    private final DataFetcher<T> dataFetcher;
    private ProcessingElementsContainer container;

    public ExtensionDataFetcherWrapper(ProcessingElementsContainer container, Class declaringClass, DataFetcher<T> dataFetcher) {
        this.container = container;
        this.declaringClass = declaringClass;
        this.dataFetcher = dataFetcher;
    }

    @Override
    public T get(DataFetchingEnvironment environment) {
        Object source = environment.getSource();
        if (source != null && (!declaringClass.isInstance(source)) && !(source instanceof Map)) {
            environment = new DataFetchingEnvironmentImpl(container.getClassFactory().newInstance(declaringClass, source), environment.getArguments(), environment.getContext(),
                    environment.getRoot(), environment.getFieldDefinition(), environment.getFields(), environment.getFieldType(), environment.getParentType(), environment.getGraphQLSchema(),
                    environment.getFragmentsByName(), environment.getExecutionId(), environment.getSelectionSet(), environment.getFieldTypeInfo());
        }

        return dataFetcher.get(environment);
    }

    public DataFetcher<T> getUnwrappedDataFetcher() {
        return dataFetcher;
    }

}
