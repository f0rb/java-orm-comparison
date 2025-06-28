/*
 * Copyright © 2019-2023 Forb Yuan
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
 * limitations under the License.
 */

package win.doyto.ormcamparison.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;

/**
 * CustomerEntity
 *
 * @author f0rb on 2023/2/16
 */
@Getter
@Setter
@Entity
@Table(name = "customer")
public class CustomerEntity implements Persistable<Integer> {
    @Id
    @GeneratedValue
    private Integer c_custkey;
    private String c_name;
    private String c_address;
    private Integer c_nationkey;
    private String c_phone;
    private BigDecimal c_acctbal;
    private String c_mktsegment;
    private String c_comment;

    @Override
    public Integer getId() {
        return this.c_custkey;
    }

    public void setId(Integer id) {
        this.c_custkey = id;
    }

    @Override
    public boolean isNew() {
        return this.c_custkey != null;
    }
}
