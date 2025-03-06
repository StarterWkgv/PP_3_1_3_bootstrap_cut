(async () => {
    const csrfValue = document.querySelector("[name=_csrf]").value;
    const csrfHeader = "X-CSRF-TOKEN";
    const modalDelete = $('#modal-delete');
    const modalEdit = $('#modal-edit');
    const deleteFields = new Map();
    const editFields = new Map();
    const addNewUserFields = new Map();
    const errorEdit = new Map();
    const errorAddNewUser = new Map();
    let errorObj = null;
    const hideElement = e => e.style.display = "none";

    const row = user => {
        let out = '<tr>';
        Object.keys(user).forEach(k => {
            if (k === "password") return;
            out += `<td data-name=${k}> ${k === "roles" ? user[k].reduce((a, b) => a + ` ${b}`, "") : user[k]} </td>`;
        });
        out += `${but("info", user.id, "edit")} ${but("danger", user.id, "delete")}`;
        return out;
    };

    const fillMap = (modalId, fieldsMap, errorMap) => {
        document.getElementById(modalId).querySelectorAll("[data-name]").forEach(node => {
            fieldsMap.set(node.dataset.name, node);
            if (!errorMap) return;
            let errorNode = node.nextElementSibling;
            if (errorNode) errorMap.set(node.dataset.name, errorNode);
        })
    }

    fillMap("modal-delete", deleteFields);
    fillMap("modal-edit", editFields, errorEdit);
    fillMap("addNewUser", addNewUserFields, errorAddNewUser);

    const clearErrorFields = (ef) => {
        errorObj = null;
        ef.forEach(v => {
            hideElement(v);
            v.textContent = "";
        });
    };

    const clearFields = (fields) => {
        fields.forEach((v, k) => {
            if (k === "roles") {
                Array.from(v.options).forEach(opt => {
                    opt.selected = false;
                })
            } else {
                v.value = '';
            }
        })
    };

    const readFields = fields => {
        return () => {
            let user = {};
            fields.forEach((v, k) => {
                user[k] = k === "roles" ? Array.from(v.selectedOptions).map(r => r.value) : v.value;
            });
            return user;
        }
    };

    const fillInputs = fields => {
        return async (event) => {
            let row = event.relatedTarget.parentElement.parentElement;
            row.querySelectorAll('[data-name]').forEach(n => {
                let modalField = fields.get(n.dataset.name);
                if (n.dataset.name === "roles") {
                    Array.from(modalField.options).forEach(opt => {
                        opt.selected = n.textContent.includes(opt.textContent);
                    })
                } else {
                    modalField.value = n.textContent;
                }
            })
        }
    };

    const but = (c, i, t) => ` <td> <button type="button" class="btn btn-${c} px-1 py-1 "
                                                 data-uid=${i} data-toggle="modal" 
                                                 data-target="#modal-${t}"> ${t.charAt(0).toUpperCase() + t.slice(1)} 
                                                 </button></td>`;

    const fetchData = (url, modalFields, readData) => {
        return async () => {
            const met = "POST";
            try {
                let id = modalFields.get("id");
                const reqHeaders = new Headers();
                reqHeaders.append(csrfHeader, csrfValue);
                const user = readData();
                console.log(user);

                const formData = new FormData();
                for (const key in user) {
                    if (key === 'roles') {
                        for (const r in user[key]) {
                            formData.append("roles", user[key][r])
                        }
                    } else {
                        formData.append(key, user[key]);
                    }
                }
                console.log("formData:" + formData);
                const response = await fetch(`${url}${id ? '/' + id.value : ''}`, {
                    method: met, headers: reqHeaders, body: formData,
                });
                if (response.redirected) {
                    window.location.href = response.url;
                }
                if (!response.ok) {
                    alert(` couldn't perform ${met} operation, Status: ${response.status}`);
                }
            } catch (e) {
                alert(`Couldn't perform ${met} operation. Error>>> ` + e);
            }
        }
    };


    const validateAge = e => {
        try {
            let v = parseInt(e.target.value);
            if (v > 127) e.target.value = 127;
            if (v < 0) e.target.value = 0;
        } catch (e) {
            e.target.value = 0;
        }
    }

    modalDelete.on('shown.bs.modal', fillInputs(deleteFields));
    modalEdit.on('shown.bs.modal', fillInputs(editFields));
    modalEdit.on('hide.bs.modal', () => clearErrorFields(errorEdit));

    $('#nav-newUser-tab').on('hide.bs.tab', () => {
        clearFields(addNewUserFields);
        clearErrorFields(errorAddNewUser);
    });
    $('#nav-newUser-tab').on('show.bs.tab', () => {
        clearFields(addNewUserFields);
        clearErrorFields(errorAddNewUser);
    })
    $('#v-pills-admin-tab').on('show.bs.tab', async () => await updateTable());

    document.getElementById("editAge").addEventListener("input", validateAge);
    document.getElementById("newAge").addEventListener("input", validateAge);

    document.getElementById("button-delete")
        .addEventListener("click", (e) => e.target.href = `/admin/delete/${document.getElementById('deleteId').value}`);

    document.getElementById("button-edit")
        .addEventListener("click", fetchData("admin", editFields, readFields(editFields)));

    document.getElementById("button-addNewUser")
        .addEventListener("click", fetchData("admin/new", addNewUserFields, readFields(addNewUserFields)));

})()