package org.koenighotze.wiremocktryout;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author David Schmitz
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sample {
    private String publicId;
    private String data;

    private Sample(String publicId, String data) {
        this.publicId = publicId;
        this.data = data;
    }

    public Sample() {
    }

    public static Sample of(String data) {
        return new Sample(randomUUID().toString(), requireNonNull(data));
    }

    public String getData() {
        return data;
    }

    public Sample setData(String data) {
        this.data = data;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public Sample setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    @Override
    public String toString() {
        return "Sample{" +
            "data='" + data + '\'' +
            ", publicId='" + publicId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sample)) {
            return false;
        }
        Sample sample = (Sample) o;
        return Objects.equals(publicId, sample.publicId) && Objects.equals(data, sample.data);
    }

    @Override
    public int hashCode() {
        return hash(publicId, data);
    }
}
