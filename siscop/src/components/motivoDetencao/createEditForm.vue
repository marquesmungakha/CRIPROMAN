<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 600px; max-width: 60vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Motivo Detenção!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
        <q-separator />
        <q-card-section style="max-height: 50vh" class="scroll">
                <q-form class="q-gutter-md">
                    <q-list>
                        <q-item>
                            <q-item-section>
                                <q-input :value="designacao"
                                @input="$emit('update:designacao', $event)"
                                ref="designacao"
                                label="Designacao *"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza a Designacao']" />
                            </q-item-section>
                        </q-item>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createMotivoDetencao" color="teal" label="Gravar" />
            <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
        </q-card-actions>
        </q-card>
    </q-dialog>
   </div>
</template>
<script>
export default {
  props: {
    designacao: {
      type: String
    },
    show_dialog: {
      type: Boolean
    },
    listErrors: {
      type: Array
    },
    motivoDetencao: {
      type: Object
    },
    submitting: {
      type: Boolean
    },
    close: {
      type: Function
    },
    createMotivoDetencao: {
      type: Function
    },
    removeMotivoDetencao: {
      type: Function
    }
  }
}
</script>
