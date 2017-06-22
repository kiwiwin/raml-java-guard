package com.github.kiwiwin.raml.guard.helper;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;

import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceTypeHelperTest {
    @Test
    public void should_get_type_declaration_by_method_and_media_type() throws Exception {
        Resource resource = mock(Resource.class);
        Method method = mock(Method.class);
        TypeDeclaration expectedType = mock(TypeDeclaration.class);

        when(method.method()).thenReturn("post");
        when(method.body()).thenReturn(newArrayList(expectedType));
        when(expectedType.name()).thenReturn("application/json");
        when(resource.methods()).thenReturn(ImmutableList.of(method));

        ResourceTypeHelper helper = new ResourceTypeHelper(resource);

        Optional<TypeDeclaration> type = helper.type("post", "application/json");

        assertTrue(type.isPresent());
        assertThat(type.get(), is(expectedType));
    }
}